package com.sch.sch_taxi.ui.chatroom

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentChatRoomBinding
import com.sch.sch_taxi.ui.chatroom.adapter.ChatRoomAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.forEach
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
                    ChatRoomNavigationAction.NavigateToChatting -> TODO()
                    ChatRoomNavigationAction.NavigateToTaxiRoom -> TODO()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.chatRoomEvent.collectLatest { pagingData ->
                Log.d("ttt", "어뎁터 초기화??")
                chatRoomAdapter.submitData(pagingData)

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.check.collectLatest { pagingData ->
                viewModel.chatRoomEvent.collect {
                    chatRoomAdapter.submitData(it)
                    viewModel.check.value = 0

                }

            }
        }
    }

    private fun initAdapter() {
        binding.rvChat.adapter = chatRoomAdapter
    }

    override fun initAfterBinding() {
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.disconnect()
    }
}
