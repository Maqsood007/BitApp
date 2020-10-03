package com.bitfinex.bitapp.ui.tradingPair.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitfinex.bitapp.data.models.TradingPairTicker
import com.bitfinex.bitapp.data.models.TradingPairTrade
import com.bitfinex.bitapp.utils.BitAppWebSocketListener
import com.bitfinex.bitapp.utils.DataParsingUtils
import com.bitfinex.bitapp.utils.DataParsingUtils.checkIfValidData
import com.bitfinex.bitapp.utils.DataParsingUtils.extractReceivedDataChannelID
import com.bitfinex.bitapp.utils.DataParsingUtils.getTradingPairTickerItem
import com.bitfinex.bitapp.utils.DataParsingUtils.getTradingPairTickerRequest
import com.bitfinex.bitapp.utils.DataParsingUtils.getTradingPairTradeItem
import com.bitfinex.bitapp.utils.DataParsingUtils.getTradingPairTradeRequest
import com.bitfinex.bitapp.utils.DataParsingUtils.isTickerChannel
import com.bitfinex.bitapp.utils.DataParsingUtils.parseWSSubscriptionEvent
import com.bitfinex.bitapp.utils.WebSocketUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*

/**
 * Created by Muhammad Maqsood on 02/10/2020.
 */
class PairLiveTickerTradeViewModel @ViewModelInject constructor() : ViewModel(),
    BitAppWebSocketListener {

    var tradingPair: String? = null

    var tickerStreamChannelID: Long = 0
    var tradeStreamChannelID: Long = 0

    val tradingPairTicker = MutableLiveData<TradingPairTicker>()
    val tradingPairTrade = MutableLiveData<List<TradingPairTrade>>()

    lateinit var webSocket: WebSocket

    override fun onOpen(response: Response) {}

    override fun onMessage(text: String) {

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
    }

    override fun onFailure(t: Throwable) {}

    fun startListeningPairLiveTicker() {

        WebSocketUtils.startListening(this)
        webSocket = WebSocketUtils.getWebSocket()

        webSocket.send(
            getTradingPairTickerRequest(tradingPair!!)
        )
        webSocket.send(
            getTradingPairTradeRequest(tradingPair!!)
        )
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

        getTradingPairTickerItem(text, tradingPair!!)?.let { tradingPairTickerItem ->

            viewModelScope.launch(Dispatchers.Main) {
                tradingPairTicker.value = tradingPairTickerItem
            }
        }
    }

    private fun updatePairTrade(text: String) {

        getTradingPairTradeItem(text)?.let { tradingPairTradeItem ->
            viewModelScope.launch(Dispatchers.Main) {
                tradingPairTrade.value = tradingPairTradeItem
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        WebSocketUtils.clearListener()
        if (this::webSocket.isInitialized) {
            webSocket.close(1000, "Its Cleared")
        }
    }
}