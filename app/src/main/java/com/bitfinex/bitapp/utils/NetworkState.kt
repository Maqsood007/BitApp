package com.bitfinex.bitapp.utils

/**
 * Created by Muhammad Maqsood on 01/10/2020.
 */

sealed class NetworkState<out T> {

    data class Success<out T>(val data: T) : NetworkState<T>()
    data class Failure<out T>(val error: T) : NetworkState<T>()
    data class Loading<out Boolean>(val isLoading: Boolean) : NetworkState<Boolean>()
}