package com.sch.sch_taxi.ui.taxisearch

import androidx.fragment.app.viewModels
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentTaxiDetailBinding
import com.sch.sch_taxi.databinding.FragmentTaxiSearchBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TaxiSearchFragment : BaseFragment<FragmentTaxiSearchBinding, TaxiSearchViewModel>(R.layout.fragment_taxi_search) {

    private val TAG = "TaxiSearchFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_taxi_search

    override val viewModel: TaxiSearchViewModel by viewModels()
//    private val bookmarkStack2Adapter by lazy { BookCoverStack2Adapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        setupEvent()
        initAdapter()
    }

    private fun setupEvent() {
    }

    private fun initAdapter() {
//        binding.bookmarkRecycler.adapter = bookmarkStack2Adapter
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}
