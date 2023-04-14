package com.sch.sch_taxi.ui.taxisearchresult

import com.sch.domain.model.Taxis
import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaxiSearchViewModel @Inject constructor(
) : BaseViewModel(), TaxiSearchResultActionHandler {

    private val TAG = "TaxiSearchViewModel"
    private val _navigationHandler: MutableSharedFlow<TaxiSearchResultNavigationAction> =
        MutableSharedFlow<TaxiSearchResultNavigationAction>()
    val navigationHandler: SharedFlow<TaxiSearchResultNavigationAction> =
        _navigationHandler.asSharedFlow()

    private val _taxiSearchEvent: MutableStateFlow<Taxis> =
        MutableStateFlow(Taxis(emptyList()))
    val taxiSearchEvent: StateFlow<Taxis> = _taxiSearchEvent

    override fun onClickedTaxiSearchResult() {
        baseViewModelScope.launch {
            _navigationHandler.emit(TaxiSearchResultNavigationAction.NavigateToTaxiSearchResult)
        }
    }

}