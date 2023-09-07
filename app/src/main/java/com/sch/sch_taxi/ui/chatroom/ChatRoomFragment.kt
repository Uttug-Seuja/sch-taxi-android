package com.sch.sch_taxi.ui.chatroom

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import androidx.paging.map
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentChatRoomBinding
import com.sch.sch_taxi.ui.chatroom.adapter.ChatRoomAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
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

        collectLatestStateFlow(viewModel.chatRoomEvent) {
            Log.d("ttt", "어뎁터 초기화??")

            chatRoomAdapter.submitData(it)
        }

//        lifecycleScope.launchWhenStarted {
//            viewModel.chatRoomEvent.collectLatest {
//                Log.d("ttt", "어뎁터 초기화??")
//                chatRoomAdapter.submitData(it)
//            }
//        }
    }

    fun <T> Fragment.collectLatestStateFlow(flow: Flow<T>, collector: suspend (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest(collector)
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
