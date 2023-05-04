package com.sch.sch_taxi.ui.profile


import com.sch.domain.model.UserInfo
import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
) : BaseViewModel(), ProfileActionHandler {

    private val TAG = "ProfileViewModel"

    private val _navigationEvent: MutableSharedFlow<ProfileNavigationAction> =
        MutableSharedFlow<ProfileNavigationAction>()
    val navigationEvent: SharedFlow<ProfileNavigationAction> = _navigationEvent.asSharedFlow()
    val userProfile: MutableStateFlow<UserInfo?> = MutableStateFlow(null)
    val mannerTemperatureInfoState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val progressPercent: MutableStateFlow<Float> = MutableStateFlow(0F)


    init {
        baseViewModelScope.launch {

        }
    }

    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationEvent.emit(ProfileNavigationAction.NavigateToBack)
        }
    }

    override fun onClickedEditProfile() {
        baseViewModelScope.launch {
            _navigationEvent.emit(ProfileNavigationAction.NavigateToEditProfile)
        }
    }

    override fun onClickedMannerTemperatureInfo() {
        baseViewModelScope.launch {
            _navigationEvent.emit(ProfileNavigationAction.NavigateToMannerTemperatureInfo)
        }
    }

    override fun onClickedMannerWritingHistory() {
        baseViewModelScope.launch {
            _navigationEvent.emit(ProfileNavigationAction.NavigateToMannerWritingHistory)
        }
    }

    override fun onClickedMannerUsageHistory() {
        baseViewModelScope.launch {
            _navigationEvent.emit(ProfileNavigationAction.NavigateToMannerUsageHistory)
        }
    }
}