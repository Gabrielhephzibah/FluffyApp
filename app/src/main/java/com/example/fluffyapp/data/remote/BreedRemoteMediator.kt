package com.example.fluffyapp.data.remote


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.fluffyapp.data.local.database.BreedDatabase
import com.example.fluffyapp.data.local.entity.BreedEntity
import com.example.fluffyapp.data.mapper.toCatBreedEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BreedRemoteMediator(
    private val breedDb: BreedDatabase,
    private val breedApi: BreedApi
) : RemoteMediator<Int, BreedEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BreedEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    val lastPage = lastItem?.page ?: 0
                    lastPage + 1
                }
            }

            val catBreed = breedApi.getBreeds(
                limit = state.config.pageSize,
                page = loadKey
            )
            if (!catBreed.isSuccessful) {
                return MediatorResult.Error(HttpException(catBreed))
            }

            val catBreedsResponse = catBreed.body() ?: emptyList()
            val currentPageFromHeader =
                catBreed.headers()["pagination-page"]?.toIntOrNull() ?: loadKey
            breedDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    breedDb.breedDao().clearAllBreed()
                }
                val entity = catBreedsResponse.map {
                    it.toCatBreedEntity().copy(page = currentPageFromHeader)
                }
                breedDb.breedDao().insertBreeds(entity)
            }

            MediatorResult.Success(
                endOfPaginationReached = catBreed.body().isNullOrEmpty()
            )

        } catch (e: IOException) {
            println("ZIBAH ERROR:: ")
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            println("ZIBAH ERROR:: ")
            MediatorResult.Error(e)

        }
    }
}