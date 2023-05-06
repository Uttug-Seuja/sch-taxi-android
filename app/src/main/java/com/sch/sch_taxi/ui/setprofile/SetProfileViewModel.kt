package com.sch.sch_taxi.ui.setprofile

import android.util.Log
import com.sch.domain.model.Profile
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.GetUserProfileUseCase
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
    private val postFileToImageUseCase: PostFileToImageUseCase
) : BaseViewModel(), SetProfileActionHandler {

    private val TAG = "SetProfileViewModel"

    private val _navigationHandler: MutableSharedFlow<SetProfileNavigationAction> =
        MutableSharedFlow<SetProfileNavigationAction>()
    val navigationHandler: SharedFlow<SetProfileNavigationAction> =
        _navigationHandler.asSharedFlow()

    var inputContent = MutableStateFlow<String>("")
    var editTextMessageCountEvent = MutableStateFlow<Int>(0)

    var nicknameInputContent = MutableStateFlow<String>("")
    var nicknameEditTextCountEvent = MutableStateFlow<Int>(0)

    var ageInputContent = MutableStateFlow<String>("")
    var ageEditTextCountEvent = MutableStateFlow<Int>(0)

    var isManEvent = MutableStateFlow<Boolean?>(null)

    val profileImg: MutableStateFlow<Profile?> = MutableStateFlow(null)

    init {
        baseViewModelScope.launch {
            showLoading()
            getUserProfileUseCase()
                .onSuccess { profile ->
                    profileImg.emit(
                        Profile(
                            1,
                            "ddd",
                            "https://cdn-icons-png.flaticon.com/512/10089/10089718.png"
                        )
                    )
                }
            dismissLoading()
        }

        baseViewModelScope.launch {
            nicknameInputContent.debounce(0).collectLatest {
                onEditTextCount(it.length)
            }
        }

        baseViewModelScope.launch {
            ageInputContent.debounce(0).collectLatest {
                onAgeEditTextCount(it.length)
            }
        }
    }


    private fun onEditTextCount(count: Int) {
        baseViewModelScope.launch {
            nicknameEditTextCountEvent.value = count
        }
    }

    private fun onAgeEditTextCount(count: Int) {
        baseViewModelScope.launch {
            ageEditTextCountEvent.value = count
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
            if (inputContent.value == "") {
                _navigationHandler.emit(SetProfileNavigationAction.NavigateToEmpty)
            } else {
                if (idToken != null && provider != null) {
                    postRegisterUseCase(
                        idToken = idToken,
                        provider = provider,
                        name = inputContent.value,
                        schoolNum = inputContent.value,
                        gender = inputContent.value,
                        profilePath = profileImg.value!!.url,
                    ).onSuccess {
                        editor.putString("accessToken", it.accessToken)
                        editor.putString("refreshToken", it.refreshToken)
                        editor.commit()
                        val deviceId = sSharedPreferences.getString("deviceId", null)
                        val fcmToken = sSharedPreferences.getString("fcmToken", null)
//                        postNotificationTokenUseCase(deviceId = deviceId!!, token = fcmToken!!)
//                            .onSuccess {
//                                _navigationHandler.emit(SetProfileNavigationAction.NavigateToHome)
//                            }
                    }
                }
            }
            dismissLoading()
        }
    }

}