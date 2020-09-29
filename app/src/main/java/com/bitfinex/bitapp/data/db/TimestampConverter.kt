package com.bitfinex.bitapp.data.db

import androidx.room.TypeConverter
import com.bitfinex.bitapp.utils.DateTimeUtils.df
import java.text.ParseException
import java.util.*

/**
 * Created by Muhammad Maqsood on 19/09/2020.
 */
class TimestampConverter {



    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        return if (value != null) {
            try {
                val timeZone: TimeZone = TimeZone.getTimeZone("GST")
                df.timeZone = timeZone
                return df.parse(value)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            null
        } else {
            null
        }
    }


    @TypeConverter
    fun dateToTimestamp(value: Date?): String? {
        val timeZone: TimeZone = TimeZone.getTimeZone("GST")
        df.timeZone = timeZone
        return if (value == null) null else df.format(value)
    }
}