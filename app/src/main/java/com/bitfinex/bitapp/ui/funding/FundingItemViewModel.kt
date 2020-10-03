package com.bitfinex.bitapp.ui.funding

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import dagger.hilt.android.scopes.FragmentScoped
import okhttp3.*
import okio.ByteString


class FundingItemViewModel @ViewModelInject constructor() : ViewModel() {


    fun startListeningPairLiveTicker(){

        val request = Request.Builder().url("wss://api-pub.bitfinex.com/ws/2").build()

        val webSocket = OkHttpClient().newWebSocket(request, object : WebSocketListener() {

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                println("onFailure")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                println("onMessage: $text")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                println("onMessage")
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                println("onOpen")
            }
        })

        webSocket.send("{ \"event\": \"subscribe\", \"channel\": \"ticker\", \"symbol\": \"tBTCUSD\"}")

    }


}