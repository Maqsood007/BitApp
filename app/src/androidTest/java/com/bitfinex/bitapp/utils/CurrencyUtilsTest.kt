package com.bitfinex.bitapp.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

/**
 * Created by Muhammad Maqsood on 01/10/2020.
 */
@RunWith(AndroidJUnit4::class)
class CurrencyUtilsTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getCurrencySymbolSuccess() {

        val currencyCode = "USD"
        val expectedResult = "$"

        assertEquals(expectedResult, CurrencyUtils.getCurrencySymbol(currencyCode))
    }

    @Test
    fun getCurrencySymbolFalse() {

        val currencyCode = "GBP"
        val unExpectedResult = "$"

        assertNotEquals(unExpectedResult, currencyCode)
    }
}