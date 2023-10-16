package com.sch.sch_taxi.ui.home

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sch.domain.model.Reservation
import com.sch.domain.usecase.main.GetReservationUseCase
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.ui.home.adapter.createReservationPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getReservationUseCase: GetReservationUseCase
) : BaseViewModel(), HomeActionHandler {

    private val TAG = "HomeViewModel"

    private val _navigationHandler: MutableSharedFlow<HomeNavigationAction> =
        MutableSharedFlow<HomeNavigationAction>()
    val navigationHandler: SharedFlow<HomeNavigationAction> =
        _navigationHandler.asSharedFlow()

    var reservationEvent: Flow<PagingData<Reservation>> = emptyFlow()

    init {
        getReservation()

    }

    fun getReservation() {
        reservationEvent =
            createReservationPager(getReservationUseCase = getReservationUseCase).flow.cachedIn(
                baseViewModelScope
            )
    }

    override fun onClickedSearch() {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToSearch)
        }
    }

    override fun onClickedTaxiDetail(reservationId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToTaxiDetail(reservationId = reservationId))
        }
    }

    override fun onClickedNotifications() {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToNotifications)
        }
    }

    override fun onClickedTaxiCreate() {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToTaxiCreate)
        }
    }
}