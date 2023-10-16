package com.sch.sch_taxi.ui.splash

import android.util.Log
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.PostRefreshTokenUseCase
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.di.PresentationApplication.Companion.editor
import com.sch.sch_taxi.di.PresentationApplication.Companion.sSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val postRefreshTokenUseCase: PostRefreshTokenUseCase
) : BaseViewModel() {

    private val TAG = "SplashViewModel"

    private val _navigationHandler: MutableStateFlow<Int> = MutableStateFlow(0)
    val navigationHandler: StateFlow<Int> = _navigationHandler.asStateFlow()

    fun getUserToken() {
        baseViewModelScope.launch {
            val accessToken = sSharedPreferences.getString("accessToken", null)
            val refreshToken = sSharedPreferences.getString("refreshToken", null)

            if (accessToken == null) {
                _navigationHandler.value = 1
            }
            accessToken.let {
                postRefreshTokenUseCase(refreshToken!!)
                    .onSuccess {
                        editor.putString("accessToken", it.accessToken)
                        editor.putString("refreshToken", it.refreshToken)
                        editor.commit()
                        _navigationHandler.value = 2
                    }
                    .onError { exception ->
                        catchError(e = exception)
                    }
            }
        }
    }
}