package com.sch.sch_taxi.ui

import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sch.sch_taxi.NavigationGraphDirections
import com.sch.sch_taxi.base.BaseActivity
import com.sch.sch_taxi.R
import com.sch.sch_taxi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_main // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: MainViewModel by viewModels()

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    var waitTime = 0L

    override fun initStartView() {
        initNavController()
        /** DynamicLink 수신확인 */
        initDynamicLink()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

    private fun initNavController() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> showBottomNav()
                R.id.chatFragment -> showBottomNav()
                R.id.myPageFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }
        binding.bottomNavi.setupWithNavController(navController)

        // 중복터치 막기!!
        binding.bottomNavi.setOnItemReselectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {}
                R.id.chatFragment -> {}
                R.id.myPageFragment -> {}
            }
        }
    }

    private fun showBottomNav() {
        binding.bottomNavi.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavi.visibility = View.GONE
    }

    override fun onBackPressed() {
        try {
            if (onBackPressedDispatcher.hasEnabledCallbacks()) {
                super.onBackPressed()
            } else {
                when (navController.currentDestination?.id) {
                    R.id.homeFragment -> {
                        if(System.currentTimeMillis()-waitTime >= 1500) {
                            waitTime = System.currentTimeMillis()
                            Toast.makeText(this, getString(R.string.onBackPressed_Message), Toast.LENGTH_SHORT).show()
                        } else {
                            finishAffinity() // 액티비티 종료
                        }
                    }
                    null -> super.onBackPressed()
                    else -> navController.navigate(NavigationGraphDirections.actionMainFragment())
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    /** DynamicLink */
    private fun initDynamicLink() {
        val dynamicLinkData = intent.extras
        if (dynamicLinkData != null) {
            var dataStr = "DynamicLink 수신받은 값\n"
            for (key in dynamicLinkData.keySet()) {
                dataStr += "key: $key / value: ${dynamicLinkData.getString(key)}\n"
            }

        }
    }
}
