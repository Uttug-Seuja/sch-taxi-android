package com.sch.sch_taxi.ui.taxisearch

import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentTaxiDetailBinding
import com.sch.sch_taxi.databinding.FragmentTaxiSearchBinding
import com.sch.sch_taxi.ui.home.HomeFragmentDirections
import com.sch.sch_taxi.ui.home.HomeNavigationAction
import com.sch.sch_taxi.ui.taxisearch.adapter.TaxiSearchAdapter
import com.sch.sch_taxi.ui.taxisearch.adapter.TaxiSearchHistoryAdapter
import com.sch.sch_taxi.util.hideKeyboard
import com.sch.sch_taxi.util.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class TaxiSearchFragment : BaseFragment<FragmentTaxiSearchBinding, TaxiSearchViewModel>(R.layout.fragment_taxi_search) {

    private val TAG = "TaxiSearchFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_taxi_search

    override val viewModel: TaxiSearchViewModel by viewModels()
    private val taxiSearchAdapter by lazy { TaxiSearchAdapter(viewModel) }
    private val taxiSearchHistoryAdapter by lazy { TaxiSearchHistoryAdapter(viewModel) }
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
                    is TaxiSearchNavigationAction.NavigateToTaxiSearchResult -> {
//                        requireActivity().hideKeyboard()
                        navigate(TaxiSearchFragmentDirections.actionTaxiSearchFragmentToTaxiSearchResultFragment(it.searchTitle))
                    }
                    is TaxiSearchNavigationAction.NavigateToBack -> navController.popBackStack()
                }
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
        binding.rvTaxiSearch.adapter = taxiSearchAdapter
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
