package com.bitfinex.bitapp.ui.tradingPair

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bitfinex.bitapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()

    companion object {

        val TAG = HomeFragment::class.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeViewModel.getPlatformStatus()
        Log.d(TAG, "homeViewModel is: $homeViewModel")

        homeViewModel.count.observe(viewLifecycleOwner) {

            Log.d(TAG, "homeViewModel Count is : $it")
        }
    }
}