package com.bitfinex.bitapp.ui.tradingPair.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bitfinex.bitapp.R
import com.bitfinex.bitapp.data.models.TradingPairTrade
import com.bitfinex.bitapp.databinding.ListItemTrandingPairTradeBinding
import com.bitfinex.bitapp.ui.tradingPair.viewmodel.TradingPairListItemTradeViewModel

/**
 * Created by Muhammad Maqsood on 02/10/2020.
 */
class TradingPairTradesAdapter(private val tradingPairTrades: MutableList<TradingPairTrade> = mutableListOf()) :
    RecyclerView.Adapter<TradingPairTradesAdapter.TradingPairTradeItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TradingPairTradeItemViewHolder {
        val listItemBrandingPairBinding = DataBindingUtil.inflate<ListItemTrandingPairTradeBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_tranding_pair_trade,
            parent,
            false
        )

        return TradingPairTradeItemViewHolder(listItemBrandingPairBinding)
    }

    override fun onBindViewHolder(holderTrade: TradingPairTradeItemViewHolder, position: Int) {

        val tradingPair = tradingPairTrades[holderTrade.adapterPosition]
        holderTrade.bind(tradingPair)
    }

    override fun getItemCount(): Int = tradingPairTrades.size

    private fun clear() {
        tradingPairTrades.clear()
    }

    fun addTradingPairTrade(tradingPairTrade: TradingPairTrade) {
        this.tradingPairTrades.add(0, tradingPairTrade)
    }

    fun addTradingPairTrades(tradingPairTrades: List<TradingPairTrade>) {
        this.tradingPairTrades.addAll(0, tradingPairTrades)
    }

    class TradingPairTradeItemViewHolder(private val listItemTradingPairTradeBinding: ListItemTrandingPairTradeBinding) :
        RecyclerView.ViewHolder(listItemTradingPairTradeBinding.root) {

        private val tradingPairViewModel = TradingPairListItemTradeViewModel()

        fun bind(tradingPairTrade: TradingPairTrade) {
            listItemTradingPairTradeBinding.viewModel = tradingPairViewModel
            tradingPairViewModel.bind(tradingPairTrade)
        }
    }
}