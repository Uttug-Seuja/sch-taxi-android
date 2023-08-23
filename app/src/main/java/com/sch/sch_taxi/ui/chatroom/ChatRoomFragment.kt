package com.sch.sch_taxi.ui.chatroom

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentChatRoomBinding
import com.sch.sch_taxi.ui.chatroom.adapter.ChatRoomAdapter
import com.sch.sch_taxi.ui.reservationdetail.ReservationDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class ChatRoomFragment : BaseFragment<FragmentChatRoomBinding, ChatRoomViewModel>(R.layout.fragment_chat_room) {

    private val TAG = "ChatFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_chat_room

    override val viewModel: ChatRoomViewModel by viewModels()
    private val chatAdapter by lazy { ChatRoomAdapter(viewModel) }
    private val navController by lazy { findNavController() }
    private val args: ChatRoomFragmentArgs by navArgs()

    override fun initStartView() {
        viewModel.reservationId.value = args.reservationId
        viewModel.postChat()

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
                when(it) {
                    is ChatRoomNavigationAction.NavigateToBack -> navController.popBackStack()
                    ChatRoomNavigationAction.NavigateToChatting -> TODO()
                    ChatRoomNavigationAction.NavigateToTaxiRoom -> TODO()
                }
            }
        }
    }

    private fun initAdapter() {
//        binding.rvChat.adapter = notificationsAdapter
    }

    override fun initAfterBinding() {
    }
}
