package com.sch.sch_taxi.ui.taxisearchresult

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentTaxiSearchBinding
import com.sch.sch_taxi.databinding.FragmentTaxiSearchResultBinding
import com.sch.sch_taxi.ui.home.HomeFragmentDirections
import com.sch.sch_taxi.ui.taxisearch.adapter.TaxiSearchAdapter
import com.sch.sch_taxi.ui.taxisearchresult.adapter.TaxiSearchResultAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class TaxiSearchResultFragment : BaseFragment<FragmentTaxiSearchResultBinding, TaxiSearchViewModel>(R.layout.fragment_taxi_search_result) {

    private val TAG = "TaxiSearchResultFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_taxi_search_result

    override val viewModel: TaxiSearchViewModel by viewModels()
//    private val taxiSearchResultAdapter by lazy { TaxiSearchResultAdapter(viewModel) }

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
                    is TaxiSearchResultNavigationAction.NavigateToTaxiSearchResult -> navigate(HomeFragmentDirections.actionHomeFragmentToTaxiDetailFragment())
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
