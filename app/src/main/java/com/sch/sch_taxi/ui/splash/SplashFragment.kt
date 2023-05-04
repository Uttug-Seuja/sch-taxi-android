package com.sch.sch_taxi.ui.splash

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class SplashFragment :
    BaseFragment<FragmentSplashBinding, SplashViewModel>(R.layout.fragment_splash) {

    private val TAG = "SplashFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_splash

    override val viewModel: SplashViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {

        viewModel.getUserToken()

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.navigationHandler.collectLatest {
                delay(1000)
                when (it) {
                    1 -> navigate(SplashFragmentDirections.actionSplashFragmentToRegisterFragment())
                    2 -> navigate(SplashFragmentDirections.actionMainFragment())
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.white)
    }
}
