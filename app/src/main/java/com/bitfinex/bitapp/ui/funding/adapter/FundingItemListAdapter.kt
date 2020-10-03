package com.bitfinex.bitapp.ui.funding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bitfinex.bitapp.R
import com.bitfinex.bitapp.databinding.ListItemFundingItemBinding
import com.bitfinex.bitapp.ui.funding.viewmodel.FundingItemListItemViewModel

/**
 * Created by Muhammad Maqsood on 02/10/2020.
 */
class FundingItemListAdapter(private val fundingItems: MutableList<String> = mutableListOf()) :
    RecyclerView.Adapter<FundingItemListAdapter.FundingItemViewHolder>() {

    companion object {
        const val TRADING_PAIR = "fundingItem"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundingItemViewHolder {
        val listItemFundingItemBinding = DataBindingUtil.inflate<ListItemFundingItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_funding_item,
            parent,
            false
        )

        return FundingItemViewHolder(listItemFundingItemBinding)
    }

    override fun onBindViewHolder(holder: FundingItemViewHolder, position: Int) {

        val tradingPair = fundingItems[holder.adapterPosition]
        holder.bind(tradingPair)
    }

    override fun getItemCount(): Int = fundingItems.size

    private fun clear() {
        fundingItems.clear()
    }

    fun addFundingItems(fundingItems: List<String>) {
        this.fundingItems.addAll(fundingItems)
    }

    class FundingItemViewHolder(private val listItemFundingItemBinding: ListItemFundingItemBinding) :
        RecyclerView.ViewHolder(listItemFundingItemBinding.root) {

        private val fundingItemVM = FundingItemListItemViewModel()

        fun bind(fundingItem: String) {
            listItemFundingItemBinding.viewModel = fundingItemVM
            fundingItemVM.bind(fundingItem)

            listItemFundingItemBinding.root.setOnClickListener {

//                it.findNavController()
//                    .navigate(
//                        R.id.action_navigation_trading_to_pairLiveTickerTradeFragment,
//                        Bundle().apply {
//                            putString(TRADING_PAIR, fundingItem)
//                        })
            }
        }
    }
}