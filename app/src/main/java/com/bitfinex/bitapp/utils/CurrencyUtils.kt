package com.bitfinex.bitapp.utils

import java.util.*

/**
 * Created by Muhammad Maqsood on 01/10/2020.
 */
object CurrencyUtils {

    private val CURRENCY_SYMBOLS = hashMapOf<String, String>()

    fun getCurrencySymbol(currencyCode: String): String {

        if (!CURRENCY_SYMBOLS.containsKey(currencyCode)) {

            try {
                val locale = Locale("en", currencyCode)
                val currency: Currency = Currency.getInstance(currencyCode)
                val symbols = currency.getSymbol(locale)
                CURRENCY_SYMBOLS[currencyCode] = symbols
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return CURRENCY_SYMBOLS[currencyCode]!!
    }
}