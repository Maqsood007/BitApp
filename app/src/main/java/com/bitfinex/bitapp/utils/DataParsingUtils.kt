package com.bitfinex.bitapp.utils

import android.text.TextUtils.substring
import com.bitfinex.bitapp.data.models.TradingPairTicker
import com.bitfinex.bitapp.data.models.TradingPairTrade
import com.bitfinex.bitapp.data.models.WSSubscriptionResponse
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.lang.reflect.Type
import java.math.BigDecimal

/**
 * Created by Muhammad Maqsood on 01/10/2020.
 */
object DataParsingUtils {

    enum class TradingPairIndex {
        FIRST,
        SECOND
    }

    enum class ChannelType {
        TICKER,
        TRADES
    }

    enum class PairTradeType {
        NORMAL,
        TRADING_EXECUTION,
        TRADING_UPDATE
    }

    fun isPlatformIsUp(data: List<String?>?) = data?.get(0)?.let {
        return it == "1"
    } ?: false

    fun getCurrencyFromTradingPair(
        tradingPair: String,
        tradingPairIndex: TradingPairIndex
    ): String {

        when (tradingPairIndex) {
            TradingPairIndex.FIRST -> {

                return if (tradingPair.contains(":")) {
                    tradingPair.split(":")[0]
                } else {
                    substring(tradingPair, 0, (tradingPair.length / 2))
                }
            }
            TradingPairIndex.SECOND -> {

                return if (tradingPair.contains(":")) {
                    tradingPair.split(":")[1]
                } else {
                    substring(tradingPair, tradingPair.length / 2, tradingPair.length)
                }
            }
        }
    }

    // Differentiate between channel (ticker or trades)
    fun isTickerChannel(wsSubscriptionResponse: WSSubscriptionResponse) =
        wsSubscriptionResponse.channel == ChannelType.TICKER.name.toLowerCase()

    // Parse subscription event
    fun parseWSSubscriptionEvent(data: String): WSSubscriptionResponse {
        val type: Type = object : TypeToken<WSSubscriptionResponse>() {}.type
        return Gson().fromJson(data, type)
    }

    fun extractReceivedDataChannelID(
        data: String,
        tickerChannelID: Long
    ): ChannelType {

        val type: Type = object : TypeToken<JsonArray>() {}.type
        val response = Gson().fromJson<JsonArray>(data, type)

        // channelID (always)
        val channelIDElement = response.get(0)
        val typeString: Type = object : TypeToken<Long>() {}.type
        return if (Gson().fromJson<Long>(
                channelIDElement,
                typeString
            ) == tickerChannelID
        ) return ChannelType.TICKER else ChannelType.TRADES
    }

    fun checkIfValidData(data: String) = (!data.contains("hb") && !data.contains("event"))

    fun getTradingPairTickerItem(data: String, pairName: String): TradingPairTicker? {

        // If there is no activity in the channel for 15 seconds,
        // the WebSocket server will send you a heartbeat message in this format.
        try {

            if (checkIfValidData(data)) {

                val type: Type = object : TypeToken<JsonArray>() {}.type
                val response = Gson().fromJson<JsonArray>(data, type)

                val dataParsed = response.get(1)
                val typeListBigDecimal: Type = object : TypeToken<List<BigDecimal>>() {}.type
                val dataList = Gson().fromJson<List<BigDecimal>>(dataParsed, typeListBigDecimal)

                return TradingPairTicker(
                    pairName,
                    dataList[7],
                    dataList[5],
                    dataList[8]
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    private fun getPairTradeType(data: String) = when {
        data.contains("tu") -> PairTradeType.TRADING_UPDATE
        data.contains("te") -> PairTradeType.TRADING_EXECUTION
        else -> PairTradeType.NORMAL
    }

    fun getTradingPairTradeItem(data: String): List<TradingPairTrade>? {

        // If there is no activity in the channel for 15 seconds,
        // the WebSocket server will send you a heartbeat message in this format.
        try {

            if (checkIfValidData(data)) {

                when (getPairTradeType(data)) {
                    PairTradeType.NORMAL -> {

                        val type: Type = object : TypeToken<JsonArray>() {}.type
                        val response = Gson().fromJson<JsonArray>(data, type)

                        val dataParsed = response.get(1)
                        val typeListJsonArray: Type = object : TypeToken<List<JsonArray>>() {}.type
                        val dataJsonArrayList =
                            Gson().fromJson<List<JsonArray>>(dataParsed, typeListJsonArray)

                        val list = mutableListOf<TradingPairTrade>()

                        dataJsonArrayList.forEach {

                            val typeListBigDecimal: Type =
                                object : TypeToken<List<BigDecimal>>() {}.type
                            val dataList = Gson().fromJson<List<BigDecimal>>(it, typeListBigDecimal)

                            list.add(
                                TradingPairTrade(
                                    dataList[1].toLong(),
                                    dataList[3],
                                    dataList[2]
                                )
                            )

                            println()
                        }

                        return list
                    }
                    PairTradeType.TRADING_EXECUTION -> {
                        return getTradesExecutionAndUpdate(data)
                    }
                    PairTradeType.TRADING_UPDATE -> {
                        return getTradesExecutionAndUpdate(data)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    private fun getTradesExecutionAndUpdate(data: String): List<TradingPairTrade> {

        val type: Type = object : TypeToken<JsonArray>() {}.type
        val response = Gson().fromJson<JsonArray>(data, type)

        val dataParsed = response.get(2)
        val typeListJsonArray: Type = object : TypeToken<List<BigDecimal>>() {}.type
        val dataJsonArrayList =
            Gson().fromJson<List<BigDecimal>>(dataParsed, typeListJsonArray)

        val list = mutableListOf<TradingPairTrade>()

        list.add(
            TradingPairTrade(
                dataJsonArrayList[1].toLong(),
                dataJsonArrayList[3],
                dataJsonArrayList[2]
            )
        )

        return list
    }

    fun getTradingPairSymbol(tradingPair: String) = "t${
        tradingPair.replace(
            "/",
            ""
        )
    }"
}