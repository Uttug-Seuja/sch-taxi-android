package com.sch.sch_taxi.ui.mypage

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.AlertDialogModel
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.base.DefaultRedAlertDialog
import com.sch.sch_taxi.base.DefaultYellowAlertDialog
import com.sch.sch_taxi.databinding.FragmentMyPageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page) {

    private val TAG = "MyPageFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_my_page

    override val viewModel: MyPageViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        setupEvent()
    }

    private fun setupEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is MyPageNavigationAction.NavigateToProfile -> navigate(
                        MyPageFragmentDirections.actionMyPageFragmentToProfileFragment(-1)
                    )

                    is MyPageNavigationAction.NavigateToMyReservation -> navigate(
                        MyPageFragmentDirections.actionMyPageFragmentToMyPostFragment()
                    )

                    is MyPageNavigationAction.NavigateToMyParticipation -> navigate(
                        MyPageFragmentDirections.actionMyPageFragmentToMyParticipationFragment()
                    )

                    is MyPageNavigationAction.NavigateToAlarmSetting -> navigate(
                        MyPageFragmentDirections.actionMyPageFragmentToAlarmSettingFragment()
                    )

                    is MyPageNavigationAction.NavigateToRegister -> navigate(
                        MyPageFragmentDirections.actionRegisterFragment()
                    )

                    is MyPageNavigationAction.NavigateToLogoutDialog -> logOutDialog()
                }
            }
        }

    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }


    private fun logOutDialog() {
        val res = AlertDialogModel(
            title = getString(R.string.logout_title),
            description = getString(R.string.logout_description),
            positiveContents = getString(R.string.logout),
            negativeContents = getString(R.string.no)
        )
        val dialog = DefaultYellowAlertDialog(
            alertDialogModel = res,
            clickToPositive = {
                toastMessage("로그아웃 되었습니다")
                viewModel.onUserLogOut()
            },
            clickToNegative = {
//                toastMessage("취소")
            }
        )
        dialog.show(childFragmentManager, TAG)
    }

    private fun userDeleteDialog() {
        val res = AlertDialogModel(
            title = getString(R.string.user_delete_title),
            description = getString(R.string.user_delete_description),
            positiveContents = getString(R.string.user_delete_yes),
            negativeContents = getString(R.string.no)
        )
        val dialog: DefaultRedAlertDialog = DefaultRedAlertDialog(
            alertDialogModel = res,
            clickToPositive = {
                toastMessage("회원 탈퇴")
                viewModel.onUserDelete()
            },
            clickToNegative = {
                toastMessage("취소")
            }
        )
        dialog.show(childFragmentManager, TAG)
    }

    override fun onResume() {
        viewModel.getProfile()
        super.onResume()
    }
}
