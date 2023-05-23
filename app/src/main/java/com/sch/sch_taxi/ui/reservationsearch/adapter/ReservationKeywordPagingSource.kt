package com.sch.sch_taxi.ui.reservationsearch.adapter

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sch.domain.fold
import com.sch.domain.model.Keyword
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.GetReservationKeywordUseCase

fun createReservationKeywordPager(
    getReservationKeywordUseCase: GetReservationKeywordUseCase,
    keyword: String
): Pager<Int, Keyword> = Pager(
    config = PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = {
        ReservationKeywordPagingSource(
            getReservationKeywordUseCase = getReservationKeywordUseCase,
            keyword
        )
    }
)

class ReservationKeywordPagingSource(
    private val getReservationKeywordUseCase: GetReservationKeywordUseCase,
    private val keyword: String
) : PagingSource<Int, Keyword>() {

    override fun getRefreshKey(state: PagingState<Int, Keyword>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Keyword> {
        Log.d("Ttt load", "load")

        val pageIndex = params.key ?: 0
        val result = getReservationKeywordUseCase(
            word = keyword,
            page = pageIndex,
            size = params.loadSize,
        ).onSuccess {
            Log.d("Ttt onSuccess", it.toString())
        }.onError {
            Log.d("Ttt onError",  it.toString())
        }
        return result.fold(
            onSuccess = { contents ->
                LoadResult.Page(
                    data = contents.content,
                    prevKey = null,
                    nextKey = if (!contents.last) pageIndex + 1 else null
                )
            },
            onError = { e -> LoadResult.Error(e) }
        )
    }

}