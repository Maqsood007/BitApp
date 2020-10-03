package com.bitfinex.bitapp.ui.tradingPair.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bitfinex.bitapp.data.models.TradingPairTicker

/**
 * Created by Muhammad Maqsood on 02/10/2020.
 */
class TradingPairListItemTickerViewModel : ViewModel() {


    private val name = MutableLiveData<String>()
    private val lastPrice = MutableLiveData<String>()
    private val dailyChange = MutableLiveData<String>()
    private val volume = MutableLiveData<String>()

    fun bind(tradingPair: TradingPairTicker) {

        name.value = tradingPair.name
        lastPrice.value = tradingPair.lastPrice.toInt().toString()
        dailyChange.value = tradingPair.dailyChange.toString()
        volume.value = tradingPair.volume.toString()

    }


    fun getName() = name.value
    fun getLastPrice() = lastPrice.value
    fun getDailyChange() = dailyChange.value
    fun getVolume() = volume.value


}