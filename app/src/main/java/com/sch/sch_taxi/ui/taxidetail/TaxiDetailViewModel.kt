package com.sch.sch_taxi.ui.taxidetail

import com.sch.domain.model.Taxis
import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaxiDetailViewModel @Inject constructor(
) : BaseViewModel(), TaxiDetailActionHandler {

    private val TAG = "TaxiDetailViewModel"

    private val _navigationHandler: MutableSharedFlow<TaxiDetailNavigationAction> =
        MutableSharedFlow<TaxiDetailNavigationAction>()
    val navigationHandler: SharedFlow<TaxiDetailNavigationAction> =
        _navigationHandler.asSharedFlow()

    private val _notificationsEvent: MutableStateFlow<Taxis> =
        MutableStateFlow(Taxis(emptyList()))
    val notificationsEvent: StateFlow<Taxis> = _notificationsEvent
    var editTextReportEvent = MutableStateFlow<String>("")

    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationHandler.emit(TaxiDetailNavigationAction.NavigateToBack)
        }
    }

    override fun onClickedTaxiMoreBottomDialog() {
        baseViewModelScope.launch {
            _navigationHandler.emit(
                TaxiDetailNavigationAction.NavigateToTaxiMoreBottomDialog(
                    0,1
                )
            )
        }
    }

    fun onClickedTaxiUpdateClicked() {
        baseViewModelScope.launch {
        }
    }

    fun onTaxiDeleteClicked(taxiId : Int) {
        baseViewModelScope.launch {

        }
    }

    fun onClickedUserReport(sendUserId : Int) {
        baseViewModelScope.launch {

        }
    }
    fun onClickedReport(taxiId : Int, reportReason : String) {
        baseViewModelScope.launch {

        }
    }


}