package com.sch.sch_taxi.ui.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentHomeBinding
import com.sch.sch_taxi.ui.home.adapter.ReservationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    private val TAG = "HomeFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    override val viewModel: HomeViewModel by viewModels()
    private val reservationAdapter by lazy { ReservationAdapter(viewModel) }

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
                    is HomeNavigationAction.NavigateToTaxiDetail -> navigate(HomeFragmentDirections.actionHomeFragmentToTaxiDetailFragment())
                    is HomeNavigationAction.NavigateToSearch -> navigate(HomeFragmentDirections.actionHomeFragmentToTaxiSearchFragment())
                    is HomeNavigationAction.NavigateToTaxiCreate -> navigate(HomeFragmentDirections.actionHomeFragmentToTaxiCreateFragment())
                    is HomeNavigationAction.NavigateToNotifications -> navigate(HomeFragmentDirections.actionHomeFragmentToNotificationFragment())
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.reservationEvent.collectLatest {
                reservationAdapter.submitData(it)
            }
        }
    }
    private fun initAdapter() {
        binding.rvReservation.adapter = reservationAdapter
    }

    override fun initAfterBinding() {
    }
}
