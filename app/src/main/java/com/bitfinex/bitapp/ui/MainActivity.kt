package com.bitfinex.bitapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bitfinex.bitapp.R
import com.bitfinex.bitapp.databinding.ActivityMainBinding
import com.bitfinex.bitapp.utils.DataParsingUtils
import com.bitfinex.bitapp.utils.NetworkState
import com.bitfinex.bitapp.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val homeViewModel by viewModels<HomeSharedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayout()

        setSupportActionBar(toolbar)

        setupViews(getNavController())

        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (NetworkUtils.isNetworkAvailable(this)) {
            setupSplashObserver()
        } else {
            homeViewModel.splashStatusMessage.value = getString(R.string.no_internet_msg)
            homeViewModel.splashLoadingVisibility.value = false
        }
    }

    private fun setupSplashObserver() {

        homeViewModel.getPlatformStatus().observe(this) {

            when (it) {
                is NetworkState.Success -> {
                    @Suppress("UNCHECKED_CAST")
                    val data = it.data as List<String>
                    if (DataParsingUtils.isPlatformIsUp(data)) {
                        hideSplash()
                    } else {
                        homeViewModel.splashStatusMessage.value =
                            getString(R.string.platform_not_up)
                    }
                    homeViewModel.splashLoadingVisibility.value = false
                }
                is NetworkState.Failure -> {
                    homeViewModel.splashStatusMessage.value = it.error as String
                    homeViewModel.splashLoadingVisibility.value = false
                }
                is NetworkState.Loading -> {
                    homeViewModel.splashLoadingVisibility.value = true
                }
            }
        }
    }

    private fun hideSplash() {
        homeViewModel.splashVisibility.value = false
    }

    private fun setLayout() {
        val bindings = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        bindings.lifecycleOwner = this
        bindings.viewModel = homeViewModel
    }

    private fun getNavController(): NavController {

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

    private fun setupViews(navController: NavController) {

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_trading, R.id.navigation_fundingItem
            )
        )
        toolbar.setupWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return getNavController().navigateUp() || super.onSupportNavigateUp()
    }
}