package com.sch.sch_taxi.ui.setprofile

import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.GetAssetRandomUseCase
import com.sch.domain.usecase.main.PostEmailCodeUseCase
import com.sch.domain.usecase.main.PostEmailUseCase
import com.sch.domain.usecase.main.PostFileToImageUseCase
import com.sch.domain.usecase.main.PostNotificationTokenUseCase
import com.sch.domain.usecase.main.PostRegisterUseCase
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.di.PresentationApplication.Companion.editor
import com.sch.sch_taxi.di.PresentationApplication.Companion.sSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.*

@HiltViewModel
class SetProfileViewModel @Inject constructor(
    private val postRegisterUseCase: PostRegisterUseCase,
    private val postFileToImageUseCase: PostFileToImageUseCase,
    private val postNotificationTokenUseCase: PostNotificationTokenUseCase,
    private val postEmailUseCase: PostEmailUseCase,
    private val postEmailCodeUseCase: PostEmailCodeUseCase,
    private val getAssetRandomUseCase: GetAssetRandomUseCase,
) : BaseViewModel(), SetProfileActionHandler {

    private val TAG = "SetProfileViewModel"

    private val _navigationHandler: MutableSharedFlow<SetProfileNavigationAction> =
        MutableSharedFlow<SetProfileNavigationAction>()
    val navigationHandler: SharedFlow<SetProfileNavigationAction> =
        _navigationHandler.asSharedFlow()

    var nicknameInputContent = MutableStateFlow<String>("")
    var nicknameEditTextCountEvent = MutableStateFlow<Int>(0)

    var schoolEmailInputContent = MutableStateFlow<String>("")

    var schoolEmailCodeInputContent = MutableStateFlow<String>("")

    var isManEvent = MutableStateFlow<Boolean?>(null)

    var isAuthEvent = MutableStateFlow<Boolean>(false)

    var isSchoolEmailAuth = MutableStateFlow<Boolean>(false)

    val profileImgPath: MutableStateFlow<String?> = MutableStateFlow(null)

    init {
        baseViewModelScope.launch {
            showLoading()
            getAssetRandomUseCase().onSuccess { userInFo ->
                    profileImgPath.emit(
                        userInFo.url
                    )
                }
            dismissLoading()
        }

        baseViewModelScope.launch {
            nicknameInputContent.debounce(0).collectLatest {
                onEditTextCount(it.length)
            }
        }
    }


    private fun onEditTextCount(count: Int) {
        baseViewModelScope.launch {
            nicknameEditTextCountEvent.value = count
        }
    }

    override fun onGenderManClicked() {
        baseViewModelScope.launch {
            isManEvent.value = true
        }

    }

    override fun onGenderWomanClicked() {
        baseViewModelScope.launch {
            isManEvent.value = false
        }
    }

    override fun onSchoolEmailAuthClicked() {
        showLoading()
        val provider = sSharedPreferences.getString("provider", null)
        isAuthEvent.value = true
        baseViewModelScope.launch {
            showLoading()

            postEmailUseCase(
                email = schoolEmailInputContent.value,
                oauthProvider = provider!!
            ).onSuccess {
                    isAuthEvent.value = true
                    _navigationHandler.emit(
                        SetProfileNavigationAction.NavigateToToastMessage(
                            "인증 메일을 보냈습니다"
                        )
                    )
                }.onError {
                    isAuthEvent.value = false
                    _navigationHandler.emit(
                        SetProfileNavigationAction.NavigateToToastMessage(
                            "학교 이메일 형식에 맞지 않습니다"
                        )
                    )
                }
            dismissLoading()

        }
    }

    override fun onSchoolEmailCodeAuthClicked() {
        baseViewModelScope.launch {
            showLoading()
            val provider = sSharedPreferences.getString("provider", null)

            postEmailCodeUseCase(
                email = schoolEmailInputContent.value,
                code = schoolEmailCodeInputContent.value,
                oauthProvider = provider!!
            ).onSuccess {
                isSchoolEmailAuth.value = true
                _navigationHandler.emit(
                    SetProfileNavigationAction.NavigateToToastMessage(
                        "인증되었습니다"
                    )
                )
            }.onError {
                isSchoolEmailAuth.value = false
                _navigationHandler.emit(
                    SetProfileNavigationAction.NavigateToToastMessage(
                        "인증 코드가 틀립니다"
                    )
                )
            }
            dismissLoading()
        }
    }

    override fun onSelectionDoneClicked() {
        baseViewModelScope.launch {
            showLoading()
            val idToken = sSharedPreferences.getString("idToken", null)
            val provider = sSharedPreferences.getString("provider", null)
            if (nicknameInputContent.value == "") baseViewModelScope.launch {
                _navigationHandler.emit(
                    SetProfileNavigationAction.NavigateToToastMessage(
                        "이름을 입력해주세요"
                    )
                )
            }
            else if (isManEvent.value == null) baseViewModelScope.launch {
                _navigationHandler.emit(
                    SetProfileNavigationAction.NavigateToToastMessage(
                        "성별을 선택해주세요"
                    )
                )
            }
            else if (schoolEmailInputContent.value == "") baseViewModelScope.launch {
                _navigationHandler.emit(
                    SetProfileNavigationAction.NavigateToToastMessage("학교 이메일을 입력주세요")
                )
            }
            else if (schoolEmailCodeInputContent.value == "") baseViewModelScope.launch {
                _navigationHandler.emit(
                    SetProfileNavigationAction.NavigateToToastMessage("인증 코드를 입력해주세요")
                )
            }
            else if (!isSchoolEmailAuth.value) baseViewModelScope.launch {
                _navigationHandler.emit(
                    SetProfileNavigationAction.NavigateToToastMessage("인증 코드가 틀립니다.")
                )
            }
            else {
                if (idToken != null && provider != null) {

                    postRegisterUseCase(
                        idToken = idToken,
                        provider = provider,
                        name = nicknameInputContent.value,
                        gender = when (isManEvent.value!!) {
                            true -> "MAN"
                            false -> "WOMAN"
                        },
                        profilePath = profileImgPath.value!!,
                        schEmail = schoolEmailInputContent.value
                    ).onSuccess {
                        editor.putString("accessToken", it.accessToken)
                        editor.putString("refreshToken", it.refreshToken)
                        editor.commit()
                        /**
                         * fcm 등록 코드
                         * */
                        val deviceId = sSharedPreferences.getString("deviceId", null)
                        val fcmToken = sSharedPreferences.getString("fcmToken", null)
                        postNotificationTokenUseCase(
                            deviceId = deviceId!!,
                            token = fcmToken!!
                        ).onSuccess {
                                _navigationHandler.emit(SetProfileNavigationAction.NavigateToHome)
                            }
                    }.onError {}
                }
            }
            dismissLoading()
        }
    }

}