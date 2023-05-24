package com.sch.sch_taxi.ui.mypage


import com.sch.domain.model.UserInfo
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.GetUserProfileUseCase
import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase
) : BaseViewModel(), MyPageActionHandler {

    private val TAG = "MyPageViewModel"

    private val _navigationEvent: MutableSharedFlow<MyPageNavigationAction> =
        MutableSharedFlow<MyPageNavigationAction>()
    val navigationEvent: SharedFlow<MyPageNavigationAction> = _navigationEvent.asSharedFlow()
    val userProfile: MutableStateFlow<UserInfo?> = MutableStateFlow(null)

    init {
        baseViewModelScope.launch {
            getUserProfileUseCase().onSuccess {
                userProfile.emit(it)
            }
        }
    }

    fun getProfile() {
        baseViewModelScope.launch {
            showLoading()
            getUserProfileUseCase()
                .onSuccess { profile ->
                    userProfile.emit(profile)
                }
            dismissLoading()
        }
    }

    override fun onClickedProfile() {
        baseViewModelScope.launch {
            _navigationEvent.emit(MyPageNavigationAction.NavigateToProfile)
        }
    }

    override fun onClickedMyReservation() {
        baseViewModelScope.launch {
            _navigationEvent.emit(MyPageNavigationAction.NavigateToMyReservation)
        }
    }

    override fun onClickedMyParticipation() {
        baseViewModelScope.launch {
            _navigationEvent.emit(MyPageNavigationAction.NavigateToMyParticipation)
        }
    }

    override fun onClickedAlarmSetting() {
        baseViewModelScope.launch {
            _navigationEvent.emit(MyPageNavigationAction.NavigateToAlarmSetting)
        }
    }

    fun onUserLogOut() {
        baseViewModelScope.launch {
            showLoading()
//            mainRepository.postLogOut()
//                .onSuccess {
//                    editor.remove("access_token")
//                    editor.remove("refresh_token")
//                    editor.commit()
//                    _navigationEvent.emit(EditProfileNavigationAction.NavigateToSplash)
//                }
            dismissLoading()
        }
    }

    fun onUserDelete() {
        baseViewModelScope.launch {
            showLoading()
//            mainRepository.postLogOut()
//                .onSuccess {
//                    editor.remove("access_token")
//                    editor.remove("refresh_token")
//                    editor.commit()
//                    _navigationEvent.emit(EditProfileNavigationAction.NavigateToSplash)
//                }
            dismissLoading()
        }
    }
}