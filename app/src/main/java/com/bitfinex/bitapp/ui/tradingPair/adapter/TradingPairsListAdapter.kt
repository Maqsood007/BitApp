package com.bitfinex.bitapp.ui.tradingPair.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bitfinex.bitapp.R
import com.bitfinex.bitapp.databinding.ListItemTrandingPairBinding
import com.bitfinex.bitapp.ui.tradingPair.viewmodel.TradingPairListItemViewModel

/**
 * Created by Muhammad Maqsood on 02/10/2020.
 */
class TradingPairsListAdapter(private val tradingPairs: MutableList<String> = mutableListOf()) :
    RecyclerView.Adapter<TradingPairsListAdapter.TradingPairItemViewHolder>() {

    companion object {

        const val TRADING_PAIR = "trading_pair"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradingPairItemViewHolder {
        val listItemBrandingPairBinding = DataBindingUtil.inflate<ListItemTrandingPairBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_tranding_pair,
            parent,
            false
        )

        return TradingPairItemViewHolder(listItemBrandingPairBinding)
    }

    override fun onBindViewHolder(holder: TradingPairItemViewHolder, position: Int) {

        val tradingPair = tradingPairs[holder.adapterPosition]
        holder.bind(tradingPair)
    }

    override fun getItemCount(): Int = tradingPairs.size

    private fun clear() {
        tradingPairs.clear()
    }

    fun addTradingPair(products: List<String>) {
        this.tradingPairs.addAll(products)
    }

    class TradingPairItemViewHolder(private val listItemBrandingPairBinding: ListItemTrandingPairBinding) :
        RecyclerView.ViewHolder(listItemBrandingPairBinding.root) {

        private val tradingPairViewModel = TradingPairListItemViewModel()

        fun bind(tradingPair: String) {
            listItemBrandingPairBinding.viewModel = tradingPairViewModel
            tradingPairViewModel.bind(tradingPair)

            listItemBrandingPairBinding.root.setOnClickListener {

                it.findNavController()
                    .navigate(
                        R.id.action_navigation_trading_to_pairLiveTickerTradeFragment,
                        Bundle().apply {
                            putString(TRADING_PAIR, tradingPair)
                        })
            }
        }
    }
}