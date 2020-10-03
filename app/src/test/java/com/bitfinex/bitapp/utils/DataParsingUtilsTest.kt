package com.bitfinex.bitapp.utils

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by Muhammad Maqsood on 02/10/2020.
 */
class DataParsingUtilsTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun parseTradingPairs() {
    }

    @Test
    fun getFirstCurrencyFromTradingPair() {

        val input = "AED:USD"
        val expected = "AED"

        assertEquals(
            expected,
            DataParsingUtils.getCurrencyFromTradingPair(
                input,
                DataParsingUtils.TradingPairIndex.FIRST
            )
        )

    }

    @Test
    fun getSecondCurrencyFromTradingPair() {

        val input = "AEDUSD"
        val expected = "USD"

        assertEquals(
            expected,
            DataParsingUtils.getCurrencyFromTradingPair(
                input,
                DataParsingUtils.TradingPairIndex.SECOND
            )
        )

    }
}