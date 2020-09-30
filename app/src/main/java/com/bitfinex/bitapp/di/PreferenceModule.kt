package com.bitfinex.bitapp.di

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.bitfinex.bitapp.utils.AppConstants.BIT_APP_PREF
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * Created by Muhammad Maqsood on 29/09/2020.
 */

@Module
@InstallIn(ApplicationComponent::class)
object PreferenceModule {

    @Provides
    fun providePreference(application: Application): SharedPreferences {

        val masterKey = MasterKey.Builder(application)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences
            .create(
                application,
                BIT_APP_PREF,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    }
}