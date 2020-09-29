package com.bitfinex.bitapp.data

import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Muhammad Maqsood on 29/09/2020.
 */
interface BitAppAPI {


    @GET("platform/status")
    suspend fun getPlatformStatus(
    ): JsonArray

    @GET("conf/pub:list:pair:exchange")
    suspend fun getTradingPairs(
    ): JsonArray

    @GET("conf/pub:list:currency")
    suspend fun getFundingItems(
    ): JsonArray

}