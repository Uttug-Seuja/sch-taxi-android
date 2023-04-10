package com.sch.sch_taxi.ui.taxicreate

import androidx.fragment.app.viewModels
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentNotificationsBinding
import com.sch.sch_taxi.databinding.FragmentTaxiCreateBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TaxiCreateFragment : BaseFragment<FragmentTaxiCreateBinding, TaxiCreateViewModel>(R.layout.fragment_taxi_create) {

    private val TAG = "TaxiCreateFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_taxi_create

    override val viewModel: TaxiCreateViewModel by viewModels()

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
