package com.sch.sch_taxi.ui.mypage

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentMyPageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page) {

    private val TAG = "MyPageFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_my_page

    override val viewModel: MyPageViewModel by viewModels()

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
//                when (it) {
//                    is MyPageNavigationAction.NavigateToEditProfile -> navigate(
//                        MyPageFragmentDirections.actionMyPageFragmentToEditProfileFragment()
//                    )
//                    is MyPageNavigationAction.NavigateToAlarmSetting -> navigate(
//                        MyPageFragmentDirections.actionMyPageFragmentToAlarmSettingFragment()
//                    )
//                    is MyPageNavigationAction.NavigateToMyFavorite -> navigate(
//                        MyPageFragmentDirections.actionMyPageFragmentToMyFavoriteFragment()
//                    )
//                }
            }
        }

    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}
