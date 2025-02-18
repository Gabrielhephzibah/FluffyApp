package com.example.fluffyapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState

class FakePagingSource<T : Any>(
    private val items: List<T>
) : PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return LoadResult.Page(
            data = items,
            prevKey = null,
            nextKey = null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null
}