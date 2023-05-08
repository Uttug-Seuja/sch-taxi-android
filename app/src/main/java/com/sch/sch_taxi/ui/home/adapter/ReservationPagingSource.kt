package com.sch.sch_taxi.ui.home.adapter

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sch.domain.fold
import com.sch.domain.model.Reservation
import com.sch.domain.usecase.main.GetReservationUseCase

fun createReservationPager(
    getReservationUseCase: GetReservationUseCase
): Pager<Int, Reservation> = Pager(
    config = PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = {
        ReservationPagingSource(
            getReservationUseCase = getReservationUseCase
        )
    }
)

class ReservationPagingSource(
    private val getReservationUseCase: GetReservationUseCase
) : PagingSource<Int, Reservation>() {

    override fun getRefreshKey(state: PagingState<Int, Reservation>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Reservation> {
        val pageIndex = params.key ?: 0
        val result = getReservationUseCase(
            page = pageIndex,
            size = params.loadSize,
        )
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