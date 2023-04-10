package com.sch.sch_taxi.ui.taxidetail

import androidx.fragment.app.viewModels
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentNotificationsBinding
import com.sch.sch_taxi.databinding.FragmentTaxiDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TaxiDetailFragment : BaseFragment<FragmentTaxiDetailBinding, TaxiDetailViewModel>(R.layout.fragment_taxi_detail) {

    private val TAG = "TaxiDetailFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_taxi_detail

    override val viewModel: TaxiDetailViewModel by viewModels()
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
