package com.bitfinex.bitapp.ui.home

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.bitfinex.bitapp.data.BitAppAPI
import com.google.gson.JsonArray
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.launch

@FragmentScoped
class HomeViewModel @ViewModelInject constructor(
    private val bitAppAPI: BitAppAPI,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    val  count = MutableLiveData<Int>(0)

    fun getPlatformStatus() = viewModelScope.launch {

        count.value = count.value?.plus(1)

        val response : JsonArray = bitAppAPI.getTradingPairs()
        print("RESPONSE: $response")

    }

    override fun onCleared() {
        super.onCleared()
    }

}