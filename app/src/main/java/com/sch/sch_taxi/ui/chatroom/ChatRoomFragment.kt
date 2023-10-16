package com.sch.sch_taxi.ui.chatroom

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.AlertDialogModel
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.base.DefaultRedAlertDialog
import com.sch.sch_taxi.databinding.FragmentChatRoomBinding
import com.sch.sch_taxi.ui.chatroom.adapter.ChatRoomAdapter
import com.sch.sch_taxi.ui.chatroom.bottom.BottomChatRoomMore
import com.sch.sch_taxi.ui.reservationcreate.bottom.BottomSelectSeat
import com.sch.sch_taxi.ui.chatroom.bottom.ChatRoomMoreType
import com.sch.sch_taxi.ui.reservationdetail.bottom.BottomTaxiReport
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ChatRoomFragment :
    BaseFragment<FragmentChatRoomBinding, ChatRoomViewModel>(R.layout.fragment_chat_room) {

    private val TAG = "ChatFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_chat_room

    override val viewModel: ChatRoomViewModel by viewModels()
    private val chatRoomAdapter by lazy { ChatRoomAdapter(viewModel) }
    private val navController by lazy { findNavController() }
    private val args: ChatRoomFragmentArgs by navArgs()

    override fun initStartView() {
        viewModel.reservationId.value = args.reservationId
        viewModel.postChat()
        viewModel.runStomp()
        viewModel.getReservationDetail()
        viewModel.getParticipation()

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
                    is ChatRoomNavigationAction.NavigateToBack -> navController.popBackStack()
                    is ChatRoomNavigationAction.NavigateToReservationMoreBottomDialog -> reservationMoreBottomDialog(
                        reservationId = it.reservationId, sendUserId = it.sendUserId
                    )
                    is ChatRoomNavigationAction.NavigateToSelectSeatBottomDialog -> selectSeatBottomDialog()
                    is ChatRoomNavigationAction.NavigateToUserProfile -> {
                        navigate(
                            ChatRoomFragmentDirections.actionChatRoomFragmentToProfileFragment(
                                it.userId
                            )
                        )
                    }

                    is ChatRoomNavigationAction.NavigateToReservationUpdate -> selectSeatBottomDialog()
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.newMessageCheck.collectLatest { pagingData ->
                viewModel.chatRoomEvent.collect {
                    chatRoomAdapter.submitData(it)
                }
            }
        }
    }

    private fun initAdapter() {
        binding.rvChat.adapter = chatRoomAdapter

        binding.rvChat.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (viewModel.isEditMessageSend.value) {
                    binding.rvChat.smoothScrollToPosition(0)
                    viewModel.isEditMessageSend.value = false
                }
            }
        })
    }

    override fun initAfterBinding() {
    }

    private fun selectSeatBottomDialog() {
        val dialog: BottomSelectSeat =
            BottomSelectSeat((viewModel.participationEvent.value!!.participationInfoList.ParticipationInfo)) {
                lifecycleScope.launchWhenStarted {
                    val seatPosition = when (it) {
                        1 -> "SEAT_1"
                        2 -> "SEAT_2"
                        3 -> "SEAT_3"
                        4 -> "SEAT_4"
                        else -> "SEAT_1"

                    }
                    viewModel.onClickedPatchParticipation(seatPosition = seatPosition)
                }
            }
        dialog.show(childFragmentManager, TAG)
    }

    private fun reservationMoreBottomDialog(
        reservationId: Int, sendUserId: Int
    ) {
        val dialog: BottomChatRoomMore = BottomChatRoomMore() {
            when (it) {
                is ChatRoomMoreType.Update -> viewModel.onClickedSelectSeatBottomDialog()
                is ChatRoomMoreType.Delete -> viewModel.onClickedDeleteParticipation()
                is ChatRoomMoreType.UserDeclare -> usersBlockDialog(sendUserId = sendUserId)
                is ChatRoomMoreType.Report -> reportDialog(reservationId = reservationId)
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

    private fun reportDialog(reservationId: Int) {
        val bottomSheet = BottomTaxiReport(
        ) { reportReason ->
            toastMessage("게시글를 신고했습니다")
            viewModel.onClickedReport(reservationId = reservationId, reportReason = reportReason)
        }
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.disconnect()
    }
}
