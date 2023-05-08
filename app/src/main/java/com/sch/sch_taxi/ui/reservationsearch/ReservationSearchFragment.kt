package com.sch.sch_taxi.ui.reservationsearch

import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentReservationSearchBinding
import com.sch.sch_taxi.ui.reservationsearch.adapter.ReservationKeywordAdapter
import com.sch.sch_taxi.ui.reservationsearch.adapter.ReservationSearchHistoryAdapter
import com.sch.sch_taxi.util.hideKeyboard
import com.sch.sch_taxi.util.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class ReservationSearchFragment : BaseFragment<FragmentReservationSearchBinding, ReservationSearchViewModel>(R.layout.fragment_reservation_search) {

    private val TAG = "TaxiSearchFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_reservation_search

    override val viewModel: ReservationSearchViewModel by viewModels()
    private val reservationKeywordAdapter by lazy { ReservationKeywordAdapter(viewModel) }
    private val taxiSearchHistoryAdapter by lazy { ReservationSearchHistoryAdapter(viewModel) }
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        toastMessage = viewModel.toastMessage
        initAdapter()
        initEditText()
    }


    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is ReservationSearchNavigationAction.NavigateToTaxiSearchResult -> {
//                        requireActivity().hideKeyboard()
                        navigate(ReservationSearchFragmentDirections.actionTaxiSearchFragmentToTaxiSearchResultFragment(it.searchTitle))
                    }
                    is ReservationSearchNavigationAction.NavigateToBack -> navController.popBackStack()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.reservationSearchEvent.collectLatest {
                reservationKeywordAdapter.submitData(it)
            }
        }
    }

    private fun initEditText(){
        binding.etSearchField.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                // 엔터 눌렀을때 행동
                viewModel.createSearchHistory()
                return@setOnKeyListener  true
            }

            return@setOnKeyListener false
        }
    }

    private fun initAdapter() {
        binding.rvReservationSearch.adapter = reservationKeywordAdapter
        binding.rvTaxiSearchHistory.adapter = taxiSearchHistoryAdapter
    }

    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSearchHistoryList()
        requireActivity().showKeyboard(binding.etSearchField)

    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().hideKeyboard()
    }
}
