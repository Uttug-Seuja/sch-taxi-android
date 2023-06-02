package com.sch.sch_taxi.ui.myreservation

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sch.domain.model.Reservation
import com.sch.domain.model.Taxis
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.GetUserReservationUseCase
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.ui.home.HomeNavigationAction
import com.sch.sch_taxi.ui.home.adapter.createReservationPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyReservationViewModel @Inject constructor(
    private val getUserReservationUseCase: GetUserReservationUseCase
) : BaseViewModel(), MyReservationActionHandler {

    private val TAG = "MyReservationViewModel"

    private val _navigationHandler: MutableSharedFlow<MyReservationNavigationAction> =
        MutableSharedFlow<MyReservationNavigationAction>()
    val navigationHandler: SharedFlow<MyReservationNavigationAction> =
        _navigationHandler.asSharedFlow()

    private val _userReservationEvent: MutableStateFlow<List<Reservation>> =
        MutableStateFlow(listOf())
    val userReservationEvent: StateFlow<List<Reservation>> = _userReservationEvent


    init {
        getUserReservation()
    }

    fun getUserReservation() {
        baseViewModelScope.launch {
            getUserReservationUseCase()
                .onSuccess {
                    _userReservationEvent.value = it
                }
                .onError {
                    Log.d("ttt onError", it.toString())
                }
        }
    }

    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationHandler.emit(MyReservationNavigationAction.NavigateToBack)
        }
    }

    override fun onClickedTaxiDetail(reservationId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(MyReservationNavigationAction.NavigateToTaxiDetail(reservationId = reservationId))
        }
    }
}