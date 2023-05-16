package com.sch.sch_taxi.ui.myreservation

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentMyPostBinding
import com.sch.sch_taxi.ui.myreservation.adapter.MyReservationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MyReservationFragment : BaseFragment<FragmentMyPostBinding, MyReservationViewModel>(R.layout.fragment_my_post) {

    private val TAG = "MyPostFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_my_post

    override val viewModel: MyReservationViewModel by viewModels()
    private val notificationsAdapter by lazy { MyReservationAdapter(viewModel) }
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
                    is MyReservationNavigationAction.NavigateToBack -> navController.popBackStack()
                    is MyReservationNavigationAction.NavigateToChatting -> TODO()
                    is MyReservationNavigationAction.NavigateToTaxiRoom -> TODO()
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
