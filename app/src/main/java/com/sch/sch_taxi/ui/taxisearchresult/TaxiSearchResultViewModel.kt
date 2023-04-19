package com.sch.sch_taxi.ui.taxisearchresult

import android.util.Log
import com.sch.domain.model.Taxis
import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaxiSearchResultViewModel @Inject constructor(
) : BaseViewModel(), TaxiSearchResultActionHandler {

    private val TAG = "TaxiSearchViewModel"
    private val _navigationHandler: MutableSharedFlow<TaxiSearchResultNavigationAction> =
        MutableSharedFlow<TaxiSearchResultNavigationAction>()
    val navigationHandler: SharedFlow<TaxiSearchResultNavigationAction> =
        _navigationHandler.asSharedFlow()

    private val _taxiSearchEvent: MutableStateFlow<Taxis> =
        MutableStateFlow(Taxis(emptyList()))
    val taxiSearchEvent: StateFlow<Taxis> = _taxiSearchEvent

    var searchTitleEvent: MutableStateFlow<String> = MutableStateFlow("")

    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationHandler.emit(TaxiSearchResultNavigationAction.NavigateToBack)
        }
    }

    override fun onClickedDeleteSearchTitle() {
        Log.d("ttt", "??")
        baseViewModelScope.launch{
            searchTitleEvent.value = ""
        }
    }


    override fun onClickedTaxiSearchResult() {
        baseViewModelScope.launch {
            _navigationHandler.emit(TaxiSearchResultNavigationAction.NavigateToTaxiSearchResult)
        }
    }

}