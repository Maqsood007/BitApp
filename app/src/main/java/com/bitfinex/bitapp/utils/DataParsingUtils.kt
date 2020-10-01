package com.bitfinex.bitapp.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import java.lang.reflect.Type

/**
 * Created by Muhammad Maqsood on 01/10/2020.
 */
object DataParsingUtils {

    fun parseTradingPairs(data: JsonElement, type: Type) = Gson().fromJson<List<String>>(data, type)
}