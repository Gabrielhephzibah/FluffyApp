package com.example.fluffyapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.fluffyapp.data.remote.BreedRemoteMediator
import com.example.fluffyapp.data.local.dao.BreedDao
import com.example.fluffyapp.data.local.dao.FavouriteBreedDao
import com.example.fluffyapp.data.local.database.BreedDatabase
import com.example.fluffyapp.data.mapper.toBreedDetail
import com.example.fluffyapp.data.mapper.toCatBreed
import com.example.fluffyapp.data.mapper.toFavouriteBreed
import com.example.fluffyapp.data.mapper.toFavouriteEntity
import com.example.fluffyapp.data.remote.BreedApi
import com.example.fluffyapp.domain.model.BreedDetail
import com.example.fluffyapp.domain.model.Breed
import com.example.fluffyapp.domain.model.FavouriteBreed
import com.example.fluffyapp.domain.repository.BreedRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class BreedRepositoryImpl @Inject constructor(
    private val breedApi: BreedApi,
    private val breedDb: BreedDatabase,
    private val breedDao: BreedDao,
    private val favouriteDao: FavouriteBreedDao,
    private val ioDispatcher: CoroutineDispatcher
) : BreedRepository {
    override fun getBreeds(searchQuery: String?): Flow<PagingData<Breed>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                initialLoadSize = 20,
            ),
            remoteMediator = if (searchQuery.isNullOrEmpty()) {
                BreedRemoteMediator(
                    breedDb = breedDb,
                    breedApi = breedApi
                )
            } else null,
            pagingSourceFactory = {
                breedDao.getBreeds(searchQuery)
            }
        ).flow.map { pagingData -> pagingData.map { it.toCatBreed() } }.flowOn(ioDispatcher)

    }

    override suspend fun insertFavouriteBreed(favouriteBreed: FavouriteBreed) {
        favouriteDao.insertFavouriteBreed(favouriteBreed.toFavouriteEntity())
    }

    override fun getFavoriteBreedId(): Flow<List<String>> {
        return favouriteDao.getFavouritesId()
    }

    override suspend fun removeBreedFromFavourite(id: String) {
        favouriteDao.removeBreedFromFavourite(id)
    }

    override fun getFavoriteBreeds(): Flow<List<FavouriteBreed>> {
        return favouriteDao.getFavouriteBreeds()
            .map { entity ->
                entity.map { it.toFavouriteBreed() }
            }
    }

    override fun getBreedDetail(id: String): Flow<BreedDetail> {
        return breedDao.findBreedById(id).map { it.toBreedDetail() }
    }
}