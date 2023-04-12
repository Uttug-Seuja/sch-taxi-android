package com.sch.sch_taxi.ui.taxisearch

import com.sch.domain.model.Taxis
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.ui.home.HomeNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaxiSearchViewModel @Inject constructor(
) : BaseViewModel(), TaxiSearchActionHandler {

    private val TAG = "TaxiSearchViewModel"
    private val _navigationHandler: MutableSharedFlow<TaxiSearchNavigationAction> =
        MutableSharedFlow<TaxiSearchNavigationAction>()
    val navigationHandler: SharedFlow<TaxiSearchNavigationAction> =
        _navigationHandler.asSharedFlow()

    private val _taxiSearchEvent: MutableStateFlow<Taxis> =
        MutableStateFlow(Taxis(emptyList()))
    val taxiSearchEvent: StateFlow<Taxis> = _taxiSearchEvent

    override fun onClickedTaxiSearchResult() {
        baseViewModelScope.launch {
            _navigationHandler.emit(TaxiSearchNavigationAction.NavigateToTaxiSearchResult)
        }
    }

}