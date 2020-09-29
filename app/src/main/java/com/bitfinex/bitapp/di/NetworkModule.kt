package com.bitfinex.bitapp.di

/**
 * Created by Muhammad Maqsood on 29/09/2020.
 */

import com.bitfinex.bitapp.BuildConfig
import com.bitfinex.bitapp.data.BitAppAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor {
                val request = it.request()

                val newUrl: HttpUrl?
                newUrl = request.url.newBuilder()
                    .build()


                it.proceed(
                    request.newBuilder()
                        .url(newUrl)
                        .build()
                )

            }
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    fun converterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    fun retrofit(httpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}

@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {

    @Provides
    internal fun bitAppAPI(retrofit: Retrofit): BitAppAPI = retrofit.create(
        BitAppAPI::class.java
    )
}
