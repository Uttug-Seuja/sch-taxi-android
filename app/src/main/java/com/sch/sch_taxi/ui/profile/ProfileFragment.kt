package com.sch.sch_taxi.ui.profile

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {

    private val TAG = "ProfileFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_profile

    override val viewModel: ProfileViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        setupEvent()
    }

    private fun setupEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    ProfileNavigationAction.NavigateToBack -> navController.popBackStack()
                    ProfileNavigationAction.NavigateToEditProfile -> TODO()
                    ProfileNavigationAction.NavigateToMannerTemperatureInfo -> TODO()
                    ProfileNavigationAction.NavigateToMannerUsageHistory -> TODO()
                    ProfileNavigationAction.NavigateToMannerWritingHistory -> TODO()
                }
            }
        }
    }

    override fun initDataBinding() {
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initAfterBinding() {
        binding.layoutMain.setOnTouchListener { _, _ ->
            viewModel.mannerTemperatureInfoState.value = false
            false
        }
    }
}
