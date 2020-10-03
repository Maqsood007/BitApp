package com.bitfinex.bitapp.utils

import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Muhammad Maqsood on 03/10/2020.
 */
@RunWith(
    JUnit4::class
)
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