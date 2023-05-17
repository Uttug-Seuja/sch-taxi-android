package com.sch.sch_taxi.ui.saveprofile


import com.sch.domain.model.UserInfo
import com.sch.domain.usecase.main.PatchUserProfileUseCase
import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class SaveProfileViewModel @Inject constructor(
    private val patchUserProfileUseCase: PatchUserProfileUseCase
) : BaseViewModel(), SaveProfileActionHandler {

    private val TAG = "SaveProfileViewModel"

    private val _navigationEvent: MutableSharedFlow<SaveProfileNavigationAction> =
        MutableSharedFlow<SaveProfileNavigationAction>()
    val navigationEvent: SharedFlow<SaveProfileNavigationAction> = _navigationEvent.asSharedFlow()

    val editPossibleState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    val isGalleryImage: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    var beforeProfile: UserInfo? = null
    val profileImg: MutableStateFlow<String> = MutableStateFlow("")
    val profileName: MutableStateFlow<String> = MutableStateFlow("")

    init {
        baseViewModelScope.launch {
            showLoading()
//            mainRepository.getUserProfile()
//                .onSuccess {
//                    beforeProfile = it
//                    profileImg.emit(it.profile_path)
//                    profileName.emit(it.nickname)
//                }
            dismissLoading()
        }
    }

    fun setFileToUri(file: MultipartBody.Part) {
        baseViewModelScope.launch {
            showLoading()
//            mainRepository.postFileToUrl(file = file)
//                .onSuccess {
//                    profileImg.value = it.image_url
//                }
            dismissLoading()
        }
    }

    override fun onProfileImageClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(SaveProfileNavigationAction.NavigateToEditProfileImg)
        }
    }

    override fun onProfileSaveClicked() {
        baseViewModelScope.launch {
            showLoading()
            if (beforeProfile!!.profilePath != profileImg.value) {
//                mainRepository.postUserProfile(
//                    nickname = profileName.value,
//                    profile_path = profileImg.value
//                )
//                    .onSuccess {
//                        _navigationEvent.emit(SaveProfileNavigationAction.NavigateToSuccess)
//                        dismissLoading()
//                        return@launch
//                    }
            }
        }
    }
}