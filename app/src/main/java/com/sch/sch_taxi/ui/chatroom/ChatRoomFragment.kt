package com.sch.sch_taxi.ui.chatroom

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentChatBinding
import com.sch.sch_taxi.ui.chatroom.adapter.ChatAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class ChatRoomFragment : BaseFragment<FragmentChatBinding, ChatRoomViewModel>(R.layout.fragment_chat_room) {

    private val TAG = "ChatFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_chat_room

    override val viewModel: ChatRoomViewModel by viewModels()
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
