package com.bitfinex.bitapp.data.models

import java.math.BigDecimal

/**
 * Created by Muhammad Maqsood on 02/10/2020.
 */
data class TradingPairTicker(
    val name: String,
    val lastPrice: BigDecimal,
    val dailyChange: BigDecimal,
    val volume: BigDecimal
)