package com.bitfinex.bitapp.ui.funding.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Muhammad Maqsood on 02/10/2020.
 */
class FundingItemListItemViewModel : ViewModel() {

    private val currencySymbol = MutableLiveData<String>()
    private val fundingItemName = MutableLiveData<String>()

    fun bind(tradingPair: String) {
        fundingItemName.value = tradingPair
    }

    fun getCurrencySymbol() = currencySymbol.value

    fun getTradingPairName() = fundingItemName.value
}