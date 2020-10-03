package com.bitfinex.bitapp.ui

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bitfinex.bitapp.data.BitAppAPI
import com.bitfinex.bitapp.utils.FormattingUtils
import com.bitfinex.bitapp.utils.NetworkState
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.lang.reflect.Type

class HomeSharedViewModel @ViewModelInject constructor(
    private val bitAppAPI: BitAppAPI
) : ViewModel() {

    val errorDescription = MutableLiveData<String>()
    val loadingMessage = MutableLiveData<String>()
    val errorLayoutVisibility = MutableLiveData<Boolean>()
    val loadingVisibility = MutableLiveData<Boolean>()
    val retryTradingPairOverError = MutableLiveData<Boolean>()

    val splashVisibility = MutableLiveData(true)
    val splashLoadingVisibility = MutableLiveData(true)
    val splashStatusMessage = MutableLiveData<String>()

    private val tradingPairs = mutableListOf<String>()
    private val fundingItems = mutableListOf<String>()

    fun getTradingPairs() = liveData {

        emit(NetworkState.Loading(true))

        try {

            emit(
                NetworkState.Success(
                    data = if (tradingPairs.isEmpty()) {

                        val response: JsonElement = bitAppAPI.getTradingPairs().get(0)
                        val type: Type = object : TypeToken<List<String>>() {}.type
                        tradingPairs.addAll(
                            FormattingUtils.prepareTradingItemForApp(
                                Gson().fromJson(
                                    response,
                                    type
                                )
                            )
                        )
                        tradingPairs // Return the populated data list.
                    } else tradingPairs
                )
            )
        } catch (e: Exception) {
            emit(NetworkState.Failure(e.localizedMessage))
        }
    }

    fun getFundingItems() = liveData {

        emit(NetworkState.Loading(true))

        try {

            emit(
                NetworkState.Success(
                    data = if (fundingItems.isEmpty()) {

                        val response: JsonElement = bitAppAPI.getFundingItems().get(0)
                        val type: Type = object : TypeToken<List<String>>() {}.type
                        fundingItems.addAll(Gson().fromJson<List<String>>(response, type))
                        fundingItems // Return the populated data list.
                    } else fundingItems
                )
            )
        } catch (e: Exception) {
            emit(NetworkState.Failure(e.localizedMessage))
        }
    }


    fun getPlatformStatus() = liveData {

        emit(NetworkState.Loading(true))

        try {

            emit(
                NetworkState.Success(
                    data = if (fundingItems.isEmpty()) {

                        val response: JsonElement = bitAppAPI.getPlatformStatus()
                        val type: Type = object : TypeToken<List<String>>() {}.type
                        fundingItems.addAll(Gson().fromJson<List<String>>(response, type))
                        fundingItems // Return the populated data list.
                    } else fundingItems
                )
            )
        } catch (e: Exception) {
            emit(NetworkState.Failure(e.localizedMessage))
        }
    }

    fun retryOverError() {
        retryTradingPairOverError.value = true
    }
}