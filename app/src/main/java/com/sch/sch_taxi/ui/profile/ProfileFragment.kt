package com.sch.sch_taxi.ui.profile

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.AlertDialogModel
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.base.DefaultRedAlertDialog
import com.sch.sch_taxi.databinding.FragmentProfileBinding
import com.sch.sch_taxi.ui.reservationdetail.ReservationDetailFragmentArgs
import com.sch.sch_taxi.ui.reservationdetail.bottom.BottomTaxiMore
import com.sch.sch_taxi.ui.reservationdetail.bottom.BottomTaxiReport
import com.sch.sch_taxi.ui.reservationdetail.bottom.TaxiMoreType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {

    private val TAG = "ProfileFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_profile

    override val viewModel: ProfileViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val args: ProfileFragmentArgs by navArgs()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        viewModel.userId.value = args.userId
        viewModel.getProfile()
        setupEvent()
    }

    private fun setupEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is ProfileNavigationAction.NavigateToBack -> navController.popBackStack()
                    is ProfileNavigationAction.NavigateToEditProfile -> navigate(
                        ProfileFragmentDirections.actionProfileFragmentToSaveProfile()
                    )

                    is ProfileNavigationAction.NavigateToMoreBottomDialog -> moreBottomDialog(
                        sendUserId = it.sendUserId
                    )
                }
            }
        }
    }

    override fun initDataBinding() {
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initAfterBinding() {
        binding.layoutMain.setOnTouchListener { _, _ ->
            viewModel.mannerTemperatureInfoState.value = false
            false
        }
    }

    private fun moreBottomDialog(sendUserId: Int) {
        val dialog: BottomTaxiMore = BottomTaxiMore(false) {
            when (it) {
                is TaxiMoreType.Update -> {}
                is TaxiMoreType.Delete -> {}
                is TaxiMoreType.UserDeclare -> usersBlockDialog(sendUserId = sendUserId)
                is TaxiMoreType.Report -> reportDialog(sendUserId = sendUserId)
            }
        }
        dialog.show(childFragmentManager, TAG)
    }

    private fun usersBlockDialog(sendUserId: Int) {
        val res = AlertDialogModel(
            title = "이 유저를 차단할까요?",
            description = "앞으로 이 유저의 글을 볼 수 없어요",
            positiveContents = "차단하기",
            negativeContents = getString(R.string.no)
        )
        val dialog: DefaultRedAlertDialog =
            DefaultRedAlertDialog(alertDialogModel = res, clickToPositive = {
                viewModel.onClickedUserReport(sendUserId = sendUserId)
                toastMessage("유저를 차단했습니다")
            }, clickToNegative = {
                toastMessage("아니요")
            })
        dialog.show(childFragmentManager, TAG)
    }

    private fun reportDialog(sendUserId: Int) {
        val bottomSheet = BottomTaxiReport(
        ) { reportReason ->
            toastMessage("유저를 신고했습니다")
            viewModel.onClickedReport(sendUserId = sendUserId, reportReason = reportReason)
        }
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }
}
