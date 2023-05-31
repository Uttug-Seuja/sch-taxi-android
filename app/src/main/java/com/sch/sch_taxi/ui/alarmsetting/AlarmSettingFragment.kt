package com.sch.sch_taxi.ui.alarmsetting

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentAlarmSettingBinding
import com.sch.sch_taxi.ui.myparticipation.MyParticipationFragmentDirections
import com.sch.sch_taxi.ui.myparticipation.MyParticipationNavigationAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class AlarmSettingFragment :
    BaseFragment<FragmentAlarmSettingBinding, AlarmSettingViewModel>(R.layout.fragment_alarm_setting) {

    private val TAG = "AlarmSettingFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_setting

    override val viewModel: AlarmSettingViewModel by viewModels()

    private val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        viewModel.getOptions()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when (it) {
                    is AlarmSettingNavigationAction.NavigateToBack -> navController.popBackStack()
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
    }
}
