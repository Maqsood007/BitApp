package com.bitfinex.bitapp.data

import com.google.gson.JsonArray
import retrofit2.http.GET

/**
 * Created by Muhammad Maqsood on 29/09/2020.
 */
interface BitAppAPI {

    @GET("platform/status")
    suspend fun getPlatformStatus(): JsonArray

    @GET("conf/pub:list:pair:exchange")
    suspend fun getTradingPairs(): JsonArray

    @GET("conf/pub:list:currency")
    suspend fun getFundingItems(): JsonArray
}