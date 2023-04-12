package com.sch.sch_taxi.ui.taxisearch

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentTaxiDetailBinding
import com.sch.sch_taxi.databinding.FragmentTaxiSearchBinding
import com.sch.sch_taxi.ui.home.HomeFragmentDirections
import com.sch.sch_taxi.ui.home.HomeNavigationAction
import com.sch.sch_taxi.ui.taxisearch.adapter.TaxiSearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class TaxiSearchFragment : BaseFragment<FragmentTaxiSearchBinding, TaxiSearchViewModel>(R.layout.fragment_taxi_search) {

    private val TAG = "TaxiSearchFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_taxi_search

    override val viewModel: TaxiSearchViewModel by viewModels()
    private val taxiSearchAdapter by lazy { TaxiSearchAdapter(viewModel) }

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
                    is TaxiSearchNavigationAction.NavigateToTaxiSearchResult -> navigate(HomeFragmentDirections.actionHomeFragmentToTaxiDetailFragment())
                }
            }
        }
    }

    private fun initAdapter() {
        binding.rvTaxiSearch.adapter = taxiSearchAdapter
    }

    override fun initAfterBinding() {
    }
}
