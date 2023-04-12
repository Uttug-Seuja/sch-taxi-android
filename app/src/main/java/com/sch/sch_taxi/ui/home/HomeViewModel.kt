package com.sch.sch_taxi.ui.home

import com.sch.domain.model.Taxi
import com.sch.domain.model.Taxis
import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseViewModel(), HomeActionHandler {

    private val TAG = "HomeViewModel"

    private val _navigationHandler: MutableSharedFlow<HomeNavigationAction> =
        MutableSharedFlow<HomeNavigationAction>()
    val navigationHandler: SharedFlow<HomeNavigationAction> =
        _navigationHandler.asSharedFlow()

    private val _taxiEvent: MutableStateFlow<Taxis> =
        MutableStateFlow(Taxis(emptyList()))
    val taxiEvent: StateFlow<Taxis> = _taxiEvent

    init {
        getTempList()
    }

    override fun onClickedSearch() {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToSearch)
        }
    }

    override fun onClickedTaxiDetail() {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToTaxiDetail)
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

    private fun getTempList() {
        val test1 = listOf(
            Taxi("어린왕자(생택취페리 탄생 120주년 블라블라)", 1),
            Taxi("붕대 감기(윤이형 소설)", 1),
            Taxi("초록빛 힐링의 섬 아일랜드에 멈추다 하하하하", 1),
            Taxi("호모데우스(미래의 역사)", 1)
        )
        val testList = Taxis(test1)
        baseViewModelScope.launch {
            _taxiEvent.value = testList
        }
    }
}