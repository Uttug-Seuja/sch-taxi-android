package com.sch.sch_taxi.ui.register

import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.GetTokenValidationUseCase
import com.sch.domain.usecase.main.PostLoginUseCase
import com.sch.domain.usecase.main.PostNotificationTokenUseCase
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.di.PresentationApplication.Companion.editor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val getTokenValidationUseCase: GetTokenValidationUseCase,
    private val postLoginUseCase: PostLoginUseCase,
    private val postNotificationTokenUseCase: PostNotificationTokenUseCase
) : BaseViewModel(), RegisterActionHandler {

    private val TAG = "RegisterViewModel"

    private val _navigationHandler: MutableSharedFlow<RegisterNavigationAction> =
        MutableSharedFlow<RegisterNavigationAction>()
    val navigationHandler: SharedFlow<RegisterNavigationAction> = _navigationHandler.asSharedFlow()
    val notificationAgreed: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    val firebaseToken: MutableStateFlow<String> = MutableStateFlow("")
    val deviceId: MutableStateFlow<String> = MutableStateFlow("")

    fun oauthLogin(idToken: String, provider: String) {

        baseViewModelScope.launch {
            showLoading()
            getTokenValidationUseCase(idToken = idToken, provider = provider).onSuccess {
                editor.putString("idToken", idToken)
                editor.putString("provider", provider)
                editor.putString("fcmToken", firebaseToken.value)
                editor.putString("deviceId", deviceId.value)

                if (!it.isRegistered) {
                    editor.commit()
                    _navigationHandler.emit(RegisterNavigationAction.NavigateToLoginFirst)
                } else {
                    postLoginUseCase(
                        idToken = idToken,
                        provider = provider
                    ).onSuccess { response ->
                        editor.putString("accessToken", response.accessToken)
                        editor.putString("refreshToken", response.refreshToken)
                        editor.commit()

                        postNotificationTokenUseCase(
                            token = firebaseToken.value, deviceId = deviceId.value
                        ).onSuccess {
                            _navigationHandler.emit(RegisterNavigationAction.NavigateToLoginAlready)
                        }.onError {
                        }
                    }
                }
            }.onError {
            }
            dismissLoading()
        }
    }

    override fun onSendTestPushAlarmClicked() {
        baseViewModelScope.launch {

            if (!notificationAgreed.value) {

                _navigationHandler.emit(RegisterNavigationAction.NavigateToPushSetting)
            } else {

                _navigationHandler.emit(RegisterNavigationAction.NavigateToNotificationAlarm)
            }
        }
    }

    override fun onKakaoLoginClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(RegisterNavigationAction.NavigateToKakaoLogin)
        }
    }

    override fun onGoogleLoginClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(RegisterNavigationAction.NavigateToGoogleLogin)

        }
    }

}