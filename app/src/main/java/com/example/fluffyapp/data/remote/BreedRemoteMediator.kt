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

/**
 * load data from network and store it in the database
 * @param breedDb database
 * @param breedApi api
 * @return MediatorResult
 */
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

            val breedResponse = breedApi.getBreeds(
                limit = state.config.pageSize,
                page = loadKey
            )

            val responseBody = breedResponse.body() ?: emptyList()
            val currentPageFromHeader =
                breedResponse.headers()["pagination-page"]?.toIntOrNull() ?: loadKey
            breedDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    breedDb.breedDao().clearAllBreed()
                }
                val entity = responseBody.map {
                    it.toCatBreedEntity().copy(page = currentPageFromHeader)
                }
                breedDb.breedDao().insertBreeds(entity)
            }

            MediatorResult.Success(
                endOfPaginationReached = breedResponse.body().isNullOrEmpty()
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}