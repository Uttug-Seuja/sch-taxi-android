package com.sch.sch_taxi.ui.alarmsetting

import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.ui.myparticipation.MyParticipationNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmSettingViewModel @Inject constructor(
//    private val mainRepository: MainRepository
) : BaseViewModel(), AlarmSettingsActionHandler {

    private val TAG = "AlarmSettingViewModel"

    private val _alarmPushPermitted: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val alarmPushPermitted: StateFlow<Boolean> = _alarmPushPermitted

    private val _navigationHandler: MutableSharedFlow<AlarmSettingNavigationAction> =
        MutableSharedFlow<AlarmSettingNavigationAction>()
    val navigationHandler: SharedFlow<AlarmSettingNavigationAction> =
        _navigationHandler.asSharedFlow()

    fun getOptions() {
        baseViewModelScope.launch {
//            mainRepository.getOptions()
//                .onSuccess { options ->
//                    _alarmPushPermitted.value = options.new_option
//                }
        }
    }

    override fun onPushAlarmToggled(checked: Boolean) {
        baseViewModelScope.launch {
//            if(checked) mainRepository.postOptionNew()
//            else mainRepository.deleteOptionNew()
        }
    }

    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationHandler.emit(AlarmSettingNavigationAction.NavigateToBack)
        }

    }
}