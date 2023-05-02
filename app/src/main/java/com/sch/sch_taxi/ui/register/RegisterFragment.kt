package com.sch.sch_taxi.ui.register

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.provider.Settings.Secure
import android.os.Build
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.sch.sch_taxi.BuildConfig
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegisterFragment :
    BaseFragment<FragmentRegisterBinding, RegisterViewModel>(R.layout.fragment_register) {

    private val TAG = "RegisterFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_register

    override val viewModel: RegisterViewModel by viewModels()
    private val navController by lazy { findNavController() }

    private var googleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
        }

    // 권한을 허용하도록 요청
    private val requestMultiplePermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            results.forEach {
                Log.d("ttt key ", it.key)
                Log.d("ttt value", it.value.toString())

                if (!it.value) toastMessage("권한 허용 필요")
            }
        }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
//        requestMultiplePermission.launch(permissionList)

        createNotification()

    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when (it) {
                    is RegisterNavigationAction.NavigateToPushSetting -> {
                        if (!viewModel.notificationAgreed.value) {
//                            pushSettingDialog()
                            toastMessage("푸쉬 알림 전송 권한 없음")
                        } else {
                            toastMessage("푸쉬 알림 전송 완료")
                        }
                    }
                    is RegisterNavigationAction.NavigateToNotificationAlarm -> createNotification()
                    is RegisterNavigationAction.NavigateToKakaoLogin -> kakaoLogin()
                    is RegisterNavigationAction.NavigateToGoogleLogin -> googleLogin()
                    is RegisterNavigationAction.NavigateToLoginFirst -> navigate(
                        RegisterFragmentDirections.actionRegisterFragmentToSetProfileFragment()
                    )
                    is RegisterNavigationAction.NavigateToLoginAlready -> navigate(
                        RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
                    )
                }
            }
        }
    }


    override fun initAfterBinding() {
    }

    @SuppressLint("HardwareIds")
    private fun createNotification() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            viewModel.firebaseToken.value = it
            viewModel.deviceId.value =
                Secure.getString(requireContext().contentResolver, Secure.ANDROID_ID)
        }
    }

    private fun kakaoLogin() {
        val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            Log.d("ttt token", token.toString())
            Log.d("ttt error", error.toString())

            // 로그인 실패
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> toastMessage("접근이 거부 됨(동의 취소)")
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> toastMessage("유효하지 않은 앱")
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> toastMessage("인증 수단이 유효하지 않아 인증할 수 없는 상태")
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> toastMessage("요청 파라미터 오류")
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> toastMessage("유효하지 않은 scope ID")
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> toastMessage("설정이 올바르지 않음(android key hash)")
                    error.toString() == AuthErrorCause.ServerError.toString() -> toastMessage("서버 내부 에러")
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> toastMessage("앱이 요청 권한이 없음")
                    else -> toastMessage("카카오톡의 미로그인")
                }
            } else if (token != null) {
                token.idToken?.let {
                    Log.d("ttt kakao id token", it)

                    viewModel.oauthLogin(idToken = it, provider = "KAKAO")
                }
            }
        }

        // 카카오톡 설치여부 확인
        UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = kakaoCallback)
    }

    private fun googleLogin() {

        /**
         * 1. 오순택, 오순도순 jwt 회원가입 할 때 안드에서 로그인 정보
         * 2. 소셜 로그인도 마찬가지로 구글 로그인 안드에서 하고
         * 3. 그 정보를 서버에 보내는거지
         * 4. googleIdToken <- 그 이후로는 필요한 거
         * 5. 이메일, 닉네임, 프로필 이미지 등..
         * 6. 닉네임이랑 프로필 이미지 설정할 것임
         * 7. googleIdToken
         *
         * 웹 서버, 앱 서버
         * 웹 서버 개발에서는 서버에서 구글 로그인하고 코드받고 그 코드로 다시 토큰이랑 정보 받아옴.
         *
         *
         * 다른 방식일 수 있다.
         *
         * 앱 서버 개발에서는
         * 앱에서 구글 로그인하고 토큰이랑 정보를 받아올 수 있음. 그 정보를 서버에 보내줌.
         *
         *
         *
         * */

        /** 구글 클라우드 플랫폼에서 해당 프로젝트로 들어가면 OAuth 2.0 설정을 한 경우 아래와 같이 나오는데,
         *  이 중 OAuth 2.0 클라이언트 ID 밑의 "Web Client"를 클릭한다.
         *  그러면 오른쪽 위에 클라이언트 ID가 보이는데 그것을 requestIdToken에 넣으면 된다.
         *  R.string.default_web_client_id를 넣어도 작동한다고 하는데 나는 잘 되지 않는다.
         */
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_LOGIN_CLIENT_ID)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())

        val signInIntent = mGoogleSignInClient.signInIntent

        googleLauncher.launch(signInIntent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            Log.d(TAG, completedTask.result.idToken.toString())

            val idToken = completedTask.getResult(ApiException::class.java).idToken
            idToken?.let { token ->
                Log.d("ttt google id token", token)
                viewModel.oauthLogin(idToken = token, provider = "GOOGLE")
            }
        } catch (e: ApiException) {
            toastMessage("구글 로그인에 실패 하였습니다.")
        }
    }
}