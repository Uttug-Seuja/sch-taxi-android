package com.sch.sch_taxi.ui.setprofile

import android.util.Log
import com.sch.domain.model.AssetRandom
import com.sch.domain.model.Profile
import com.sch.domain.model.UserInfo
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.GetAssetRandomUseCase
import com.sch.domain.usecase.main.GetUserProfileUseCase
import com.sch.domain.usecase.main.PostEmailCodeUseCase
import com.sch.domain.usecase.main.PostEmailUseCase
import com.sch.domain.usecase.main.PostFileToImageUseCase
import com.sch.domain.usecase.main.PostRegisterUseCase
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.di.PresentationApplication.Companion.editor
import com.sch.sch_taxi.di.PresentationApplication.Companion.sSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import okhttp3.MultipartBody

@HiltViewModel
class SetProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val postRegisterUseCase: PostRegisterUseCase,
    private val postFileToImageUseCase: PostFileToImageUseCase,
    private val postEmailUseCase: PostEmailUseCase,
    private val postEmailCodeUseCase: PostEmailCodeUseCase,
    private val getAssetRandomUseCase: GetAssetRandomUseCase
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
            getAssetRandomUseCase()
                .onSuccess { userInFo ->
                    profileImgPath.emit(
                        userInFo.url
                    )
                }
                .onError {
                    Log.d("ttt error", it.toString())
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
            Log.d("ttt", "누름")
            isManEvent.value = false
        }
    }

    override fun onSchoolEmailAuthClicked() {
        isAuthEvent.value = true
        baseViewModelScope.launch {
            postEmailUseCase(email = schoolEmailInputContent.value)
                .onSuccess {
                    isAuthEvent.value = true
                    SetProfileNavigationAction.NavigateToToastMessage("인증 메일을 보냈습니다")
                }
                .onError {
                    isAuthEvent.value = false
                    SetProfileNavigationAction.NavigateToToastMessage("학교 이메일 형식에 맞지 않습니다")
                }

        }
    }

    override fun onSchoolEmailCodeAuthClicked() {
        baseViewModelScope.launch {
            postEmailCodeUseCase(
                email = schoolEmailInputContent.value,
                code = schoolEmailCodeInputContent.value
            ).onSuccess {
                SetProfileNavigationAction.NavigateToToastMessage("인증되었습니다")
                isSchoolEmailAuth.value = true
            }.onError {
                isSchoolEmailAuth.value = false
                SetProfileNavigationAction.NavigateToToastMessage("인증 코드가 틀립니다")
            }

        }
    }

    override fun onAgeSetClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SetProfileNavigationAction.NavigateToAgeNumberPicker)
        }

    }


    override fun onProfileImageSetClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SetProfileNavigationAction.NavigateToSetProfileImage)
        }
    }

    fun setFileToUri(file: MultipartBody.Part) {
        baseViewModelScope.launch {
            showLoading()
            postFileToImageUseCase(file = file)
                .onSuccess {
//                    profileImg.value = it.image_url
                }
            dismissLoading()
        }
    }

    override fun onSelectionDoneClicked() {
        baseViewModelScope.launch {
            showLoading()
            val idToken = sSharedPreferences.getString("idToken", null)
            val provider = sSharedPreferences.getString("provider", null)
            if (nicknameInputContent.value == "") _navigationHandler.emit(
                SetProfileNavigationAction.NavigateToToastMessage(
                    "이름을 입력해주세요"
                )
            )
            else if (isManEvent.value == null) _navigationHandler.emit(
                SetProfileNavigationAction.NavigateToToastMessage(
                    "성별을 선택해주세요"
                )
            )
            else if (schoolEmailInputContent.value == "") _navigationHandler.emit(
                SetProfileNavigationAction.NavigateToToastMessage("학교 이메일을 입력주세요")
            )
            else if (schoolEmailCodeInputContent.value == "") _navigationHandler.emit(
                SetProfileNavigationAction.NavigateToToastMessage("인증 코드를 입력해주세요")
            )
            else if (!isSchoolEmailAuth.value) _navigationHandler.emit(
                SetProfileNavigationAction.NavigateToToastMessage("인증 코드가 틀립니다.")
            )
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
                    ).onSuccess {
                        editor.putString("accessToken", it.accessToken)
                        editor.putString("refreshToken", it.refreshToken)
                        editor.commit()
                        val deviceId = sSharedPreferences.getString("deviceId", null)
                        val fcmToken = sSharedPreferences.getString("fcmToken", null)
//                        postNotificationTokenUseCase(deviceId = deviceId!!, token = fcmToken!!)
//                            .onSuccess {
                        _navigationHandler.emit(SetProfileNavigationAction.NavigateToHome)
//                            }
                    }
                        .onError {
                            Log.d("ttt error", it.toString())
                        }
                }
            }
            dismissLoading()
        }
    }

}