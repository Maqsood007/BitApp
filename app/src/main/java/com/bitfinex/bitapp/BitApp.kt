package com.bitfinex.bitapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Muhammad Maqsood on 29/09/2020.
 */

@HiltAndroidApp
class BitApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}