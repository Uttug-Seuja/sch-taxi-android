package com.sch.sch_taxi.ui.myparticipation

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentMyParticipationBinding
import com.sch.sch_taxi.ui.myparticipation.adapter.MyParticipationAdapter
import com.sch.sch_taxi.ui.myreservation.MyReservationFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MyParticipationFragment :
    BaseFragment<FragmentMyParticipationBinding, MyParticipationViewModel>(R.layout.fragment_my_participation) {

    private val TAG = "MyParticipationFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_my_participation

    override val viewModel: MyParticipationViewModel by viewModels()
    private val myParticipationAdapter by lazy { MyParticipationAdapter(viewModel) }
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
                    is MyParticipationNavigationAction.NavigateToBack -> navController.popBackStack()
                    is MyParticipationNavigationAction.NavigateToTaxiDetail -> navigate(
                        MyParticipationFragmentDirections.actionMyParticipationFragmentToTaxiDetailFragment(
                            it.reservationId
                        )
                    )
                }
            }
        }
    }

    private fun initAdapter() {
        binding.rvMyParticipation.adapter = myParticipationAdapter
    }

    override fun initAfterBinding() {
    }
}
