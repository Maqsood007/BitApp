package com.bitfinex.bitapp.utils

import android.text.TextUtils

/**
 * Created by Muhammad Maqsood on 02/10/2020.
 */
object FormattingUtils {


    fun prepareTradingItemForApp(tradingPairs: List<String>) = tradingPairs.map {
        getFormattedTradingPair(it)
    }


    private fun getFormattedTradingPair(tradingPair: String) = if (tradingPair.contains(":")) {
        tradingPair.replace(":", "/")
    } else {
        "${TextUtils.substring(tradingPair, 0, (tradingPair.length / 2))}/${
            TextUtils.substring(
                tradingPair,
                (tradingPair.length / 2),
                (tradingPair.length - 1)
            )
        }"
    }

}