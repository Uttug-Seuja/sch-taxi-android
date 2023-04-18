package com.sch.sch_taxi.ui.mypost

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentMyPostBinding
import com.sch.sch_taxi.databinding.FragmentNotificationsBinding
import com.sch.sch_taxi.ui.mypost.adapter.MyPostAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MyPostFragment : BaseFragment<FragmentMyPostBinding, MyPostViewModel>(R.layout.fragment_my_post) {

    private val TAG = "MyPostFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_my_post

    override val viewModel: MyPostViewModel by viewModels()
    private val notificationsAdapter by lazy { MyPostAdapter(viewModel) }
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
                    is MyPostNavigationAction.NavigateToBack -> navController.popBackStack()
                    is MyPostNavigationAction.NavigateToChatting -> TODO()
                    is MyPostNavigationAction.NavigateToTaxiRoom -> TODO()
                }
            }
        }
    }

    private fun initAdapter() {
        binding.rvMyPost.adapter = notificationsAdapter
    }

    override fun initAfterBinding() {
    }
}
