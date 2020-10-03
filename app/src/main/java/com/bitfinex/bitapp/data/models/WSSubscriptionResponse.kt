package com.bitfinex.bitapp.data.models

data class WSSubscriptionResponse(
    val symbol: String? = null,
    val channel: String? = null,
    val chanId: Long? = null,
    val event: String? = null,
    val pair: String? = null
)
