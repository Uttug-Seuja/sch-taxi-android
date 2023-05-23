package com.sch.sch_taxi.ui.reservationsearchresult

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sch.domain.model.Keyword
import com.sch.domain.model.Reservation
import com.sch.domain.usecase.main.GetReservationKeywordUseCase
import com.sch.domain.usecase.main.GetReservationSearchUseCase
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.ui.reservationsearch.adapter.createReservationKeywordPager
import com.sch.sch_taxi.ui.reservationsearchresult.adapter.createReservationSearchResultPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationSearchResultViewModel @Inject constructor(
    private val getReservationSearchUseCase: GetReservationSearchUseCase,
    private val getReservationKeywordUseCase: GetReservationKeywordUseCase
) : BaseViewModel(), ReservationSearchResultActionHandler {

    private val TAG = "ReservationSearchResultViewModel"
    private val _navigationHandler: MutableSharedFlow<ReservationSearchResultNavigationAction> =
        MutableSharedFlow<ReservationSearchResultNavigationAction>()
    val navigationHandler: SharedFlow<ReservationSearchResultNavigationAction> =
        _navigationHandler.asSharedFlow()

    var searchTitleEvent: MutableStateFlow<String> = MutableStateFlow("")

    var reservationSearchResultEvent: Flow<PagingData<Reservation>> = emptyFlow()
    var reservationSearchEvent: Flow<PagingData<Keyword>> = emptyFlow()

    init {
        getReservationSearch()
        getReservationKeywordList()
    }

    fun getReservationSearch() {
        Log.d("ttt searchTitleEvent.value", searchTitleEvent.value.toString())
        baseViewModelScope.launch {
            showLoading()
            reservationSearchResultEvent = createReservationSearchResultPager(
                getReservationSearchUseCase = getReservationSearchUseCase,
                searchTitleEvent.value
            ).flow.cachedIn(
                baseViewModelScope
            )
            dismissLoading()
        }


    }

    fun getReservationKeywordList() {
        baseViewModelScope.launch {
            showLoading()
            searchTitleEvent.debounce(200).collectLatest { keyword ->
                reservationSearchEvent = createReservationKeywordPager(
                    getReservationKeywordUseCase = getReservationKeywordUseCase,
                    keyword = keyword
                ).flow.cachedIn(
                    baseViewModelScope
                )
            }
            dismissLoading()
        }
    }

    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationHandler.emit(ReservationSearchResultNavigationAction.NavigateToBack)
        }
    }

    override fun onClickedDeleteSearchTitle() {
        baseViewModelScope.launch {
            searchTitleEvent.value = ""
        }
    }


    override fun onClickedTaxiSearchResult(searchTitle: String) {
        baseViewModelScope.launch {
            _navigationHandler.emit(ReservationSearchResultNavigationAction.NavigateToTaxiSearchResult(searchTitle= searchTitle))
        }
    }

    override fun onClickedTaxiDetail(reservationId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(
                ReservationSearchResultNavigationAction.NavigateToTaxiDetail(
                    reservationId = reservationId
                )
            )
        }
    }

}