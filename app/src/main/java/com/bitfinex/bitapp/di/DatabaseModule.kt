package com.bitfinex.bitapp.di

import android.app.Application
import androidx.room.Room
import com.bitfinex.bitapp.data.DBRepository
import com.bitfinex.bitapp.data.db.BitAppDatabase
import com.bitfinex.bitapp.utils.AppConstants.APP_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * Created by Muhammad Maqsood on 29/09/2020.
 */
@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    fun provideDataBase(application: Application): DBRepository {

        val database = Room.databaseBuilder(
            application,
            BitAppDatabase::class.java, APP_DATABASE_NAME
        ).build()

        return DBRepository((database))
    }
}