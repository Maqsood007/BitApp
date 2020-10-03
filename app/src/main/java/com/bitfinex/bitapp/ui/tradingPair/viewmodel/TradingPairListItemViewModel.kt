package com.bitfinex.bitapp.ui.tradingPair.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Muhammad Maqsood on 02/10/2020.
 */
class TradingPairListItemViewModel : ViewModel() {

    private val currencySymbol = MutableLiveData<String>()
    private val tradingPairName = MutableLiveData<String>()

    fun bind(tradingPair: String) {
        tradingPairName.value = tradingPair
//        currencySymbol.value = CurrencyUtils.getCurrencySymbol(
//            DataParsingUtils.getCurrencyFromTradingPair(
//                tradingPair,
//                DataParsingUtils.TradingPairIndex.FIRST
//            )
//        )
    }

    fun getCurrencySymbol() = currencySymbol.value

    fun getTradingPairName() = tradingPairName.value
}