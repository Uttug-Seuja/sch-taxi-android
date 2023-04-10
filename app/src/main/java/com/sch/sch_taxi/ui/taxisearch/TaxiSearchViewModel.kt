package com.sch.sch_taxi.ui.taxisearch

import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaxiSearchViewModel @Inject constructor(
) : BaseViewModel(), TaxiSearchActionHandler {

    private val TAG = "TaxiSearchViewModel"

}