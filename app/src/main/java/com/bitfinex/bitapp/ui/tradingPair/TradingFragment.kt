package com.bitfinex.bitapp.ui.tradingPair

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bitfinex.bitapp.R
import com.bitfinex.bitapp.ui.HomeSharedViewModel
import com.bitfinex.bitapp.utils.NetworkState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TradingFragment : Fragment(), TradingPairView {

    private val homeViewModel by activityViewModels<HomeSharedViewModel>()

    companion object {

        val TAG = TradingFragment::class.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addStateObserver()
        retryStateObserver()
        Log.d(TAG, homeViewModel.toString())
    }

    private fun addStateObserver() {

        homeViewModel.getTradingPairs().observe(viewLifecycleOwner) {

            when (it) {
                is NetworkState.Success -> {
                    hideLoading()
                }
                is NetworkState.Failure -> {
                    hideLoading()
                    showError(it.error as String ?: null)
                }
                is NetworkState.Loading -> {
                    hideError()
                    showLoading()
                }
            }
        }
    }

    private fun retryStateObserver() {

        homeViewModel.retryTradingPairOverError.observe(viewLifecycleOwner) { retry ->
            if (retry) {
                addStateObserver()
            }
        }
    }

    override fun hideLoading() {
        homeViewModel.loadingVisibility.value = false
    }

    override fun showLoading() {
        homeViewModel.loadingVisibility.value = true
    }

    override fun hideError() {
        homeViewModel.errorLayoutVisibility.value = false
    }

    override fun showError(error: String?) {
        homeViewModel.errorDescription.value =
            error?.let { error } ?: getString(R.string.generic_error)
        homeViewModel.errorLayoutVisibility.value = true
    }
}