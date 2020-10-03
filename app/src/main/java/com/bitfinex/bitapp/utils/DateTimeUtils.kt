package com.bitfinex.bitapp.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Muhammad Maqsood on 29/09/2020.
 */
object DateTimeUtils {

    val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)

    private val TIME_ONLY_FORMAT = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)

    fun getFormattedTime(time: Long): String {

        val date = Date(time)
        return TIME_ONLY_FORMAT.format(date)
    }
}