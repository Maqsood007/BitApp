package com.bitfinex.bitapp.ui.home

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.bitfinex.bitapp.data.BitAppAPI
import com.google.gson.JsonArray
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val bitAppAPI: BitAppAPI,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    fun getPlatformStatus() = viewModelScope.launch {

        val response : JsonArray = bitAppAPI.getTradingPairs()
        print("RESPONSE: $response")


    }

}