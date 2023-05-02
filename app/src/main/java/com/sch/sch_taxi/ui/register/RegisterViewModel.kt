package com.sch.sch_taxi.ui.register

import android.util.Log
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.remote.GetTokenValidationUseCase
import com.sch.domain.usecase.remote.PosNotificationTokenUseCase
import com.sch.domain.usecase.remote.PostLoginUseCase
import com.sch.domain.usecase.remote.PostRefreshTokenUseCase
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
    private val postNotificationTokenUseCase: PosNotificationTokenUseCase
) : BaseViewModel(), RegisterActionHandler {

    private val TAG = "RegisterViewModel"

    private val _navigationHandler: MutableSharedFlow<RegisterNavigationAction> =
        MutableSharedFlow<RegisterNavigationAction>()
    val navigationHandler: SharedFlow<RegisterNavigationAction> = _navigationHandler.asSharedFlow()
    val notificationAgreed: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    val firebaseToken: MutableStateFlow<String> = MutableStateFlow("")
    val deviceId: MutableStateFlow<String> = MutableStateFlow("")

    fun oauthLogin(idToken: String, provider: String) {
        Log.d("ttt idToken", idToken)
        Log.d("ttt provider", provider)

        baseViewModelScope.launch {
            showLoading()
            getTokenValidationUseCase(idToken = idToken, provider = provider)
                .onSuccess {
                    editor.putString("idToken", idToken)
                    editor.putString("provider", provider)
                    editor.putString("fcmToken", firebaseToken.value)
                    editor.putString("deviceId", deviceId.value)

                    if (!it.isRegistered) {
                        editor.commit()
                        _navigationHandler.emit(RegisterNavigationAction.NavigateToLoginFirst)
                    } else {
                        postLoginUseCase(idToken = idToken, provider = provider)
                            .onSuccess { response ->
                                editor.putString("accessToken", response.accessToken)
                                editor.putString("refreshToken", response.refreshToken)
                                editor.commit()

                                postNotificationTokenUseCase(
                                    token = firebaseToken.value,
                                    deviceId = deviceId.value
                                )
                                    .onSuccess {
                                        _navigationHandler.emit(RegisterNavigationAction.NavigateToLoginAlready)
                                    }
                            }
                    }
                }.onError {
                    Log.d("ttt it", it.toString())
                }
            dismissLoading()
        }
    }

    override fun onSendTestPushAlarmClicked() {
        baseViewModelScope.launch {
            Log.d("ttt", "1")

            if (!notificationAgreed.value) {
                Log.d("ttt", "3")

                _navigationHandler.emit(RegisterNavigationAction.NavigateToPushSetting)
            } else {
                Log.d("ttt", "4")

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