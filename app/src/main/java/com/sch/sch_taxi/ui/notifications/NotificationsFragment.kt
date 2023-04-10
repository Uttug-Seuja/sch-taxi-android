package com.sch.sch_taxi.ui.notifications

import androidx.fragment.app.viewModels
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentHomeBinding
import com.sch.sch_taxi.databinding.FragmentNotificationsBinding
import com.sch.sch_taxi.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding, NotificationsViewModel>(R.layout.fragment_notifications) {

    private val TAG = "NotificationsFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_notifications

    override val viewModel: NotificationsViewModel by viewModels()
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
