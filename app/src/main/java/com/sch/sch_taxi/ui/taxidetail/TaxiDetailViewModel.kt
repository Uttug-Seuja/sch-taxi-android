package com.sch.sch_taxi.ui.taxidetail

import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaxiDetailViewModel @Inject constructor(
) : BaseViewModel(), TaxiDetailActionHandler {

    private val TAG = "TaxiDetailViewModel"

}