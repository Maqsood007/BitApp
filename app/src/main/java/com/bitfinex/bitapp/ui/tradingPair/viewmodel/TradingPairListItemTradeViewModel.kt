package com.bitfinex.bitapp.ui.tradingPair.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bitfinex.bitapp.data.models.TradingPairTrade
import com.bitfinex.bitapp.utils.DateTimeUtils
import java.math.BigDecimal

/**
 * Created by Muhammad Maqsood on 02/10/2020.
 */
class TradingPairListItemTradeViewModel : ViewModel() {

    private val time = MutableLiveData<Long>()
    private val price = MutableLiveData<BigDecimal>()
    private val amount = MutableLiveData<BigDecimal>()

    fun bind(tradingPairTrade: TradingPairTrade) {

        time.value = tradingPairTrade.time
        price.value = tradingPairTrade.price
        amount.value = tradingPairTrade.amount
    }

    fun getTime() = DateTimeUtils.getFormattedTime(time.value!!)
    fun getPrice() = price.value.toString()
    fun getAmount() = amount.value.toString()
}