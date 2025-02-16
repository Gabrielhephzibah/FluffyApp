package com.example.fluffyapp.data.local


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.fluffyapp.data.local.dao.CatBreedDao
import com.example.fluffyapp.data.local.database.CatBreedDatabase
import com.example.fluffyapp.data.local.entity.CatBreedEntity
import com.example.fluffyapp.data.mapper.toCatBreedEntity
import com.example.fluffyapp.data.remote.CatBreedApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CatBreedRemoteMediator(
    private val catBreedDb: CatBreedDatabase,
    private val catBreedApi: CatBreedApi
) : RemoteMediator<Int, CatBreedEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CatBreedEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        0
                    } else {
                        catBreedDb.withTransaction {
                            catBreedDb.CatBreedDao().getCatBreeds().size / state.config.pageSize
                        }
                    }
                }
            }
            val catBreed = catBreedApi.getCat(
                limit = state.config.pageSize,
                page = loadKey
            )
            catBreedDb.withTransaction {
//              if (loadType == LoadType.REFRESH){
//                   catBreedDb.CatBreedDao().clearAll()
//                   }
                val catBreedEntity = catBreed.map { it.toCatBreedEntity() }
                catBreedDb.CatBreedDao().insertCatBreeds(catBreedEntity)
            }
            MediatorResult.Success(
                endOfPaginationReached = catBreed.isEmpty()
            )


//            val currentLoadKey = when(loadType){
//                LoadType.REFRESH -> 1
//                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//                LoadType.APPEND -> {
//                    val lastItem = state.lastItemOrNull()
//                    if (lastItem == null){
//                       return 1
//                    }else{
//                        val nextPage = catBreedDb.withTransaction {
//                            catBreedDb.CatBreedDao().getCatBreeds().size / state.config.pageSize
//                        }
//                        val catBreed = catBreedApi.getCat(
//                            limit = state.config.pageSize,
//                            page = nextPage)
//                        catBreedDb.withTransaction {
////                            if (loadType == LoadType.REFRESH){
////                              catBreedDb.CatBreedDao().clearAll()
////                            }
//                             val catBreedEntity = catBreed.map { it.toCatBreedEntity() }
//                            catBreedDb.CatBreedDao().insertCatBreeds(catBreedEntity)
//                        }
//                        MediatorResult.Success(
//                            endOfPaginationReached = catBreed.isEmpty()
//                        )
//
//                    }
//                }
//            }

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)

        }


    }
}