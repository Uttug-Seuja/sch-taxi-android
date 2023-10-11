package com.sch.sch_taxi.ui.notifications

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentNotificationsBinding
import com.sch.sch_taxi.ui.notifications.adapter.NotificationsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding, NotificationsViewModel>(R.layout.fragment_notifications) {

    private val TAG = "NotificationsFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_notifications

    override val viewModel: NotificationsViewModel by viewModels()
    private val notificationsAdapter by lazy { NotificationsAdapter(viewModel) }
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
                    is NotificationsNavigationAction.NavigateToBack -> navController.popBackStack()
                    NotificationsNavigationAction.NavigateToChatting -> TODO()
                    NotificationsNavigationAction.NavigateToTaxiRoom -> TODO()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.notificationsEvent.collectLatest {
                notificationsAdapter.submitData(it)
            }
        }
    }

    private fun initAdapter() {
        binding.rvNotifications.adapter = notificationsAdapter
    }

    override fun initAfterBinding() {
    }
}
