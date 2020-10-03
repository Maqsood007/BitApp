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
import com.bitfinex.bitapp.ui.tradingPair.adapter.TradingPairsListAdapter
import com.bitfinex.bitapp.utils.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_trading.*

@AndroidEntryPoint
class TradingPairsListFragment : Fragment(), TradingPairView {

    private val homeViewModel by activityViewModels<HomeSharedViewModel>()

    companion object {

        val TAG = TradingPairsListFragment::class.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRVAdapter()
        addSplashObserver()
        retryStateObserver()
        Log.d(TAG, homeViewModel.toString())
    }

    private fun addSplashObserver() {

        homeViewModel.splashVisibility.observe(viewLifecycleOwner) { visibility ->

            if (!visibility) {
                addStateObserver()
            }
        }
    }

    private fun addStateObserver() {

        homeViewModel.getTradingPairs().observe(viewLifecycleOwner) {

            when (it) {
                is NetworkState.Success -> {
                    hideLoading()
                    @Suppress("UNCHECKED_CAST")
                    val tradingPairs = it.data as List<String>
                    updateData(tradingPairs)
                }
                is NetworkState.Failure -> {
                    hideLoading()
                    showError(it.error as String)
                }
                is NetworkState.Loading -> {
                    hideError()
                    showLoading()
                }
            }
        }
    }

    private fun setupRVAdapter() {
        rv_tradingPairs.apply {
            adapter = TradingPairsListAdapter()
        }
    }

    private fun updateData(tradingPairs: List<String>) {

        (rv_tradingPairs.adapter as TradingPairsListAdapter).apply {
            addTradingPair(tradingPairs)
            notifyDataSetChanged()
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