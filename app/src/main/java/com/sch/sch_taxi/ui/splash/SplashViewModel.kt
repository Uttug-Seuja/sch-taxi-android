package com.sch.sch_taxi.ui.splash

import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
//    private val mainRepository: MainRepositorypository
) : BaseViewModel() {

    private val TAG = "SplashViewModel"

    private val _navigationEvent: MutableStateFlow<Int> = MutableStateFlow(0)
    val navigationEvent: StateFlow<Int> = _navigationEvent.asStateFlow()

    fun getUserToken() {
        baseViewModelScope.launch {
//            val accessToken = sSharedPreferences.getString("access_token", null)
//            val refreshToken = sSharedPreferences.getString("refresh_token", null)
//            if(accessToken == null) {
            _navigationEvent.value = 1
//            }
//
//            accessToken.let {
//                mainRepository.postRefreshToken(refreshToken!!)
//                    .onSuccess {
//                        editor.putString("access_token", it.access_token)
//                        editor.putString("refresh_token", it.refresh_token)
//                        editor.commit()
//                        _navigationHandler.value = 2 }
//                    .onError { exception ->
//                        catchError(e = exception) }
//            }
        }
    }
}