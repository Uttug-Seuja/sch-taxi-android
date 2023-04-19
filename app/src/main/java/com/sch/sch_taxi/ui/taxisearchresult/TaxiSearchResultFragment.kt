package com.sch.sch_taxi.ui.taxisearchresult

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentTaxiSearchResultBinding
import com.sch.sch_taxi.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class TaxiSearchResultFragment : BaseFragment<FragmentTaxiSearchResultBinding, TaxiSearchResultViewModel>(R.layout.fragment_taxi_search_result) {

    private val TAG = "TaxiSearchResultFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_taxi_search_result

    override val viewModel: TaxiSearchResultViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val args: TaxiSearchResultFragmentArgs by navArgs()

//    private val taxiSearchResultAdapter by lazy { TaxiSearchResultAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        viewModel.searchTitleEvent.value = args.searchResultTitle
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is TaxiSearchResultNavigationAction.NavigateToTaxiSearchResult -> navigate(HomeFragmentDirections.actionHomeFragmentToTaxiDetailFragment())
                    is TaxiSearchResultNavigationAction.NavigateToBack -> navController.popBackStack()
                }
            }
        }
    }

    private fun initAdapter() {
//        binding.rvTaxiSearch.adapter = taxiSearchAdapter
    }

    override fun initAfterBinding() {
    }
}
