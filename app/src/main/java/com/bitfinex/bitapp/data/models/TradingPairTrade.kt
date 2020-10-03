package com.bitfinex.bitapp.data.models

import java.math.BigDecimal

/**
 * Created by Muhammad Maqsood on 02/10/2020.
 */
data class TradingPairTrade(val time: Long, val price: BigDecimal, val amount: BigDecimal)