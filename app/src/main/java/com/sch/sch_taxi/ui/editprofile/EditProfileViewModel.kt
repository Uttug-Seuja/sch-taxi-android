package com.sch.sch_taxi.ui.editprofile

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
class EditProfileViewModel @Inject constructor(
) : BaseViewModel(), EditProfileActionHandler {

    private val TAG = "EditProfileViewModel"

    private val _navigationEvent: MutableSharedFlow<EditProfileNavigationAction> = MutableSharedFlow<EditProfileNavigationAction>()
    val navigationEvent: SharedFlow<EditProfileNavigationAction> = _navigationEvent.asSharedFlow()

    val userProfile: MutableStateFlow<UserInfo?> = MutableStateFlow(null)

    init {
        baseViewModelScope.launch {
            showLoading()
//            mainRepository.getUserProfile()
//                .onSuccess {
//                    userProfile.emit(it)
//                }
            dismissLoading()
        }
    }

    fun getProfile() {
        baseViewModelScope.launch {
//            mainRepository.getUserProfile()
//                .onSuccess {
//                    userProfile.emit(it)
//                }
        }
    }


    override fun onLogoutClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(EditProfileNavigationAction.NavigateToLogout)
        }
    }

    override fun onUserDeleteClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(EditProfileNavigationAction.NavigateToUserDelete)
        }
    }

    override fun onProfileEditClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(EditProfileNavigationAction.NavigateToEditProfile)
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