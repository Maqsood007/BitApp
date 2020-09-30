package com.bitfinex.bitapp.ui.tradingPair

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitfinex.bitapp.data.BitAppAPI
import com.google.gson.JsonArray
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.launch

@FragmentScoped
class HomeViewModel @ViewModelInject constructor(
    private val bitAppAPI: BitAppAPI
) : ViewModel() {

    val count = MutableLiveData<Int>(0)

    fun getPlatformStatus() = viewModelScope.launch {

        count.value = count.value?.plus(1)

        val response: JsonArray = bitAppAPI.getTradingPairs()
        print("RESPONSE: $response")
    }
}