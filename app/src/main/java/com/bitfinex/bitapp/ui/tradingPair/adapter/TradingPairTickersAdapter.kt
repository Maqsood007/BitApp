package com.bitfinex.bitapp.ui.tradingPair.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bitfinex.bitapp.R
import com.bitfinex.bitapp.data.models.TradingPairTicker
import com.bitfinex.bitapp.databinding.ListItemTradingPairTickerBinding
import com.bitfinex.bitapp.ui.tradingPair.viewmodel.TradingPairListItemTickerViewModel

/**
 * Created by Muhammad Maqsood on 02/10/2020.
 */
class TradingPairTickersAdapter(private val tradingPairTickers: MutableList<TradingPairTicker> = mutableListOf()) :
    RecyclerView.Adapter<TradingPairTickersAdapter.TradingPairTickerItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TradingPairTickerItemViewHolder {
        val listItemBrandingPairBinding = DataBindingUtil.inflate<ListItemTradingPairTickerBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_trading_pair_ticker,
            parent,
            false
        )

        return TradingPairTickerItemViewHolder(listItemBrandingPairBinding)
    }

    override fun onBindViewHolder(holderTicker: TradingPairTickerItemViewHolder, position: Int) {

        val tradingPair = tradingPairTickers[holderTicker.adapterPosition]
        holderTicker.bind(tradingPair)
    }

    override fun getItemCount(): Int = tradingPairTickers.size

    private fun clear() {
        tradingPairTickers.clear()
    }

    fun addTradingPairTicker(tradingPairTicker: TradingPairTicker) {
        this.tradingPairTickers.add(0, tradingPairTicker)
    }

    class TradingPairTickerItemViewHolder(private val listItemTradingPairTickerBinding: ListItemTradingPairTickerBinding) :
        RecyclerView.ViewHolder(listItemTradingPairTickerBinding.root) {

        private val tradingPairViewModel = TradingPairListItemTickerViewModel()

        fun bind(tradingPair: TradingPairTicker) {
            listItemTradingPairTickerBinding.viewModel = tradingPairViewModel
            tradingPairViewModel.bind(tradingPair)
        }
    }
}