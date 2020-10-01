package com.bitfinex.bitapp.ui.base

/**
 * Created by Muhammad Maqsood on 01/10/2020.
 */
interface BaseView {

    fun hideLoading()
    fun showLoading()

    fun hideError()
    fun showError(error: String?)
}