package com.sch.sch_taxi.ui.editprofile

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.AlertDialogModel
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.base.DefaultRedAlertDialog
import com.sch.sch_taxi.base.DefaultYellowAlertDialog
import com.sch.sch_taxi.databinding.FragmentEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding, EditProfileViewModel>(R.layout.fragment_edit_profile) {

    private val TAG = "EditProfileFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_edit_profile

    override val viewModel : EditProfileViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.viewmodel  = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    is EditProfileNavigationAction.NavigateToLogout -> logOutDialog()
                    is EditProfileNavigationAction.NavigateToUserDelete -> userDeleteDialog()
//                    is EditProfileNavigationAction.NavigateToEditProfile -> navigate(EditProfileFragmentDirections.actionEditProfileFragmentToSaveProfileFragment())
//                    is EditProfileNavigationAction.NavigateToSplash -> navigate(EditProfileFragmentDirections.actionRegisterFragment())
                    else -> {}
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProfile()
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = "프로필"
            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }

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
                toastMessage("로그아웃")
                viewModel.onUserLogOut()
            },
            clickToNegative = {
                toastMessage("취소")
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
}
