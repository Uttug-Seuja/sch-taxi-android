package com.sch.sch_taxi.ui.reservationsearchresult

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentReservationSearchResultBinding
import com.sch.sch_taxi.ui.home.HomeFragmentDirections
import com.sch.sch_taxi.ui.myreservation.MyReservationFragmentDirections
import com.sch.sch_taxi.ui.myreservation.MyReservationNavigationAction
import com.sch.sch_taxi.ui.reservationsearch.adapter.ReservationKeywordAdapter
import com.sch.sch_taxi.ui.reservationsearchresult.ReservationSearchResultFragmentDirections.actionTaxiSearchResultFragmentSelf
import com.sch.sch_taxi.ui.reservationsearchresult.ReservationSearchResultFragmentDirections.actionTaxiSearchResultFragmentToTaxiDetailFragment
import com.sch.sch_taxi.ui.reservationsearchresult.adapter.ReservationSearchResultAdapter
import com.sch.sch_taxi.ui.reservationsearchresult.adapter.ReservationSearchResultKeywordAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class ReservationSearchResultFragment :
    BaseFragment<FragmentReservationSearchResultBinding, ReservationSearchResultViewModel>(R.layout.fragment_reservation_search_result) {

    private val TAG = "ReservationSearchResultFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_reservation_search_result

    override val viewModel: ReservationSearchResultViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val args: ReservationSearchResultFragmentArgs by navArgs()

    private val reservationSearchResultAdapter by lazy { ReservationSearchResultAdapter(viewModel) }
    private val reservationSearchResultKeywordAdapter by lazy {
        ReservationSearchResultKeywordAdapter(
            viewModel
        )
    }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        viewModel.searchTitleEvent.value = args.searchResultTitle
        viewModel.getReservationSearch()
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when (it) {
                    is ReservationSearchResultNavigationAction.NavigateToTaxiSearchResult -> navigate(
                        actionTaxiSearchResultFragmentSelf(it.searchTitle)
                    )

                    is ReservationSearchResultNavigationAction.NavigateToBack -> navController.popBackStack()
                    is ReservationSearchResultNavigationAction.NavigateToTaxiDetail -> navigate(
                        actionTaxiSearchResultFragmentToTaxiDetailFragment(
                            it.reservationId
                        )
                    )
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.reservationSearchResultEvent.collectLatest {
                reservationSearchResultAdapter.submitData(it)
            }
        }

//        lifecycleScope.launchWhenStarted {
//            viewModel.reservationSearchEvent.collectLatest {
//                reservationSearchResultKeywordAdapter.submitData(it)
//            }
//        }
    }

    private fun initAdapter() {
        binding.rvReservationSearchResult.adapter = reservationSearchResultAdapter
        binding.rvReservationSearch.adapter = reservationSearchResultKeywordAdapter
    }

    override fun initAfterBinding() {
    }
}
