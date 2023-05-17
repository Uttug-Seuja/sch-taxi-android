package com.sch.sch_taxi.ui.myreservation

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentMyReservationBinding
import com.sch.sch_taxi.ui.myreservation.adapter.MyReservationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MyReservationFragment :
    BaseFragment<FragmentMyReservationBinding, MyReservationViewModel>(R.layout.fragment_my_reservation) {

    private val TAG = "MyReservationFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_my_reservation

    override val viewModel: MyReservationViewModel by viewModels()
    private val myReservationAdapter by lazy { MyReservationAdapter(viewModel) }
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
                    is MyReservationNavigationAction.NavigateToBack -> navController.popBackStack()
                    is MyReservationNavigationAction.NavigateToTaxiDetail -> navigate(
                        MyReservationFragmentDirections.actionMyReservationFragmentToTaxiDetailFragment(
                            it.reservationId
                        )
                    )
                }
            }
        }
    }

    private fun initAdapter() {
        binding.rvMyPost.adapter = myReservationAdapter
    }

    override fun initAfterBinding() {
    }
}
