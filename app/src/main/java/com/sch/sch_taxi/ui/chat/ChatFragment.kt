package com.sch.sch_taxi.ui.chat

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentChatBinding
import com.sch.sch_taxi.ui.chat.adapter.ChatAdapter
import com.sch.sch_taxi.ui.chatroom.ChatRoomFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding, ChatViewModel>(R.layout.fragment_chat) {

    private val TAG = "ChatFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_chat

    override val viewModel: ChatViewModel by viewModels()
    private val chatAdapter by lazy { ChatAdapter(viewModel) }
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
                    is ChatNavigationAction.NavigateToBack -> navController.popBackStack()
                    is ChatNavigationAction.NavigateToChattingRoom -> navigate(
                        ChatFragmentDirections.actionChatFragmentToChatRoomFragment(it.reservationId)
                    )

                    is ChatNavigationAction.NavigateToTaxiRoom -> TODO()
                }
            }
        }
    }

    private fun initAdapter() {
        binding.rvChatRoom.adapter = chatAdapter
    }

    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.getChatRoom()
    }
}
