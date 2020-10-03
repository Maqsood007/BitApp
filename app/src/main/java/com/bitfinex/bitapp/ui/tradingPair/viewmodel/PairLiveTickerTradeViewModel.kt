package com.bitfinex.bitapp.ui.tradingPair.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitfinex.bitapp.data.models.TradingPairTicker
import com.bitfinex.bitapp.data.models.TradingPairTrade
import com.bitfinex.bitapp.utils.DataParsingUtils
import com.bitfinex.bitapp.utils.DataParsingUtils.checkIfValidData
import com.bitfinex.bitapp.utils.DataParsingUtils.extractReceivedDataChannelID
import com.bitfinex.bitapp.utils.DataParsingUtils.getTradingPairTickerItem
import com.bitfinex.bitapp.utils.DataParsingUtils.getTradingPairTradeItem
import com.bitfinex.bitapp.utils.DataParsingUtils.isTickerChannel
import com.bitfinex.bitapp.utils.DataParsingUtils.parseWSSubscriptionEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*

/**
 * Created by Muhammad Maqsood on 02/10/2020.
 */
class PairLiveTickerTradeViewModel @ViewModelInject constructor() : ViewModel() {


    val tradingPair = MutableLiveData<String>("tBTCUSD")

    var tickerStreamChannelID: Long = 0
    var tradeStreamChannelID: Long = 0

    val tradingPairTicker = MutableLiveData<TradingPairTicker>()
    val tradingPairTrade= MutableLiveData<List<TradingPairTrade>>()


    fun startListeningPairLiveTicker() {

        val request = Request.Builder().url("wss://api-pub.bitfinex.com/ws/2").build()

        val webSocket = OkHttpClient().newWebSocket(request, object : WebSocketListener() {

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                println("onFailure")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {

                if (text.contains("\"event\":\"subscribed\"")) {
                    assignChannelsID(data = text)
                } else {

                    if (checkIfValidData(text)) {

                        when (extractReceivedDataChannelID(text, tickerStreamChannelID)) {
                            DataParsingUtils.ChannelType.TICKER -> {
                                updatePairTicker(text)
                            }
                            DataParsingUtils.ChannelType.TRADES -> {
                                updatePairTrade(text)
                            }
                        }

                    }


                }

                //update(text)
                println("onMessage: $text")
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                println("onOpen")
            }
        })


        webSocket.send("{ \"event\": \"subscribe\", \"channel\": \"ticker\", \"symbol\": \"tBTCUSD\"}")
        webSocket.send("{ \"event\": \"subscribe\", \"channel\": \"trades\", \"symbol\": \"tBTCUSD\"}")

    }


    private fun assignChannelsID(data: String) {

        val channel = parseWSSubscriptionEvent(data = data)

        if (isTickerChannel(channel)) {
            tickerStreamChannelID = channel.chanId!!
        } else {
            tradeStreamChannelID = channel.chanId!!
        }

    }


    private fun updatePairTicker(text: String) {

        getTradingPairTickerItem(text, tradingPair.value!!)?.let {tradingPairTickerItem ->

            viewModelScope.launch(Dispatchers.Main) {
                tradingPairTicker.value = tradingPairTickerItem
            }
        }

    }

    private fun updatePairTrade(text: String) {

        getTradingPairTradeItem(text)?.let {tradingPairTradeItem ->
            viewModelScope.launch(Dispatchers.Main) {
                tradingPairTrade.value = tradingPairTradeItem
            }
        }
    }


}