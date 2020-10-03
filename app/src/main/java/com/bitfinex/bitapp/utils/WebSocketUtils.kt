package com.bitfinex.bitapp.utils

import android.util.Log
import okhttp3.*

/**
 * Created by Muhammad Maqsood on 03/10/2020.
 */
object WebSocketUtils {

    private val request = Request.Builder().url(AppConstants.WebSocket.WS_BASE_URL).build()

    private var webSocketListener: BitAppWebSocketListener? = null

    fun getWebSocket() = OkHttpClient().newWebSocket(request, object : WebSocketListener() {

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            webSocketListener?.onFailure(t)
            Log.d("onFailure", t.message!!)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            webSocketListener?.onMessage(text)
            Log.d("onMessage", ": $text")
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            webSocketListener?.onOpen(response)
            Log.d("onOpen", "onOpen")
        }
    })

    fun startListening(listener: BitAppWebSocketListener) {
        webSocketListener = listener
    }

    fun clearListener() {
        webSocketListener = null
    }
}

interface BitAppWebSocketListener {
    fun onOpen(response: Response)
    fun onMessage(text: String)
    fun onFailure(t: Throwable)
}