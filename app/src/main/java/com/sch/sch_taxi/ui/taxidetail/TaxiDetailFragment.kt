package com.sch.sch_taxi.ui.taxidetail

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.AlertDialogModel
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.base.DefaultRedAlertDialog
import com.sch.sch_taxi.databinding.FragmentTaxiDetailBinding
import com.sch.sch_taxi.ui.taxidetail.bottom.BottomTaxiMore
import com.sch.sch_taxi.ui.taxidetail.bottom.BottomTaxiReport
import com.sch.sch_taxi.ui.taxidetail.bottom.TaxiMoreType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class TaxiDetailFragment :
    BaseFragment<FragmentTaxiDetailBinding, TaxiDetailViewModel>(R.layout.fragment_taxi_detail) {

    private val TAG = "TaxiDetailFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_taxi_detail

    override val viewModel: TaxiDetailViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when (it) {
                    is TaxiDetailNavigationAction.NavigateToBack -> navController.popBackStack()
                    is TaxiDetailNavigationAction.NavigateToTaxiMoreBottomDialog -> taxiMoreBottomDialog(
                        taxiId = it.taxiId,
                        sendUserId = it.sendUserId
                    )
                }
            }
        }
    }

    private fun initAdapter() {
    }

    override fun initAfterBinding() {
    }

    private fun taxiMoreBottomDialog(
        taxiId: Int,
        sendUserId: Int
    ) {
        val dialog: BottomTaxiMore = BottomTaxiMore {
            when (it) {
                is TaxiMoreType.Update -> {
                    viewModel.onClickedTaxiUpdateClicked()
                }
                is TaxiMoreType.Delete -> taxiDeleteDialog(taxiId = taxiId)
                TaxiMoreType.UserDeclare -> usersBlockDialog(sendUserId = sendUserId)
                TaxiMoreType.Report -> reportDialog(taxiId = taxiId)
            }
        }
        dialog.show(childFragmentManager, TAG)
    }

    private fun taxiDeleteDialog(taxiId: Int) {
        val res = AlertDialogModel(
            title = "이 게시글를 삭제할까요?",
            description = "게시글를 삭제하면 볼 수 없어요",
            positiveContents = "삭제하기",
            negativeContents = "취소"
        )
        val dialog: DefaultRedAlertDialog = DefaultRedAlertDialog(
            alertDialogModel = res,
            clickToPositive = {
                toastMessage("게시글를 삭제했습니다")
                // api
                viewModel.onTaxiDeleteClicked(taxiId)
            },
            clickToNegative = {
                toastMessage("아니요")
            }
        )
        dialog.show(childFragmentManager, TAG)
    }

    private fun usersBlockDialog(sendUserId: Int) {
        val res = AlertDialogModel(
            title = "이 유저를 차단할까요?",
            description = "앞으로 이 유저의 글을 볼 수 없어요",
            positiveContents = "차단하기",
            negativeContents = getString(R.string.no)
        )
        val dialog: DefaultRedAlertDialog = DefaultRedAlertDialog(
            alertDialogModel = res,
            clickToPositive = {
                viewModel.onClickedUserReport(sendUserId = sendUserId)
                toastMessage("유저를 차단했습니다")
            },
            clickToNegative = {
                toastMessage("아니요")
            }
        )
        dialog.show(childFragmentManager, TAG)
    }

    private fun reportDialog(taxiId: Int) {
        val bottomSheet = BottomTaxiReport(
        ) { reportReason ->
            toastMessage("게시글를 신고했습니다")
            viewModel.onClickedReport(taxiId = taxiId, reportReason = reportReason)
        }
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }
}
