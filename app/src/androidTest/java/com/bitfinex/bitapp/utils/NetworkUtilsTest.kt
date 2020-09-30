package com.bitfinex.bitapp.utils

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

/**
 * Created by Muhammad Maqsood on 30/09/2020.
 */
@RunWith(AndroidJUnit4::class)
class NetworkUtilsTest {

    lateinit var instrumentationContext: Context

    @Before
    fun setUp() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
    }

    @After
    fun tearDown() {
    }

    @Test
    fun isNetworkAvailable() {
        assertTrue(NetworkUtils.isNetworkAvailable(instrumentationContext))
    }

}