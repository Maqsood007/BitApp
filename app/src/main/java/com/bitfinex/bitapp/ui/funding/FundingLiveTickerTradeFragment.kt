package com.bitfinex.bitapp.ui.funding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bitfinex.bitapp.R
import com.bitfinex.bitapp.data.models.TradingPairTicker
import com.bitfinex.bitapp.data.models.TradingPairTrade
import com.bitfinex.bitapp.ui.funding.adapter.FundingItemListAdapter
import com.bitfinex.bitapp.ui.funding.viewmodel.FundingLiveTickerTradeViewModel
import com.bitfinex.bitapp.ui.tradingPair.adapter.TradingPairTickersAdapter
import com.bitfinex.bitapp.ui.tradingPair.adapter.TradingPairTradesAdapter
import kotlinx.android.synthetic.main.fragment_pair_live_ticker_trade.*

/**
 * Created by Muhammad Maqsood on 02/10/2020.
 */
class FundingLiveTickerTradeFragment : Fragment() {

    private val fundingLiveTickerTradeViewModel by viewModels<FundingLiveTickerTradeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_funding_live_ticker_trade, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupTickerRVAdapter()
        setupTradingRVAdapter()

        getTradingPairFromArgument()

        fundingLiveTickerTradeViewModel.startListeningPairLiveTicker()
        fundingLiveTickerTradeViewModel.tradingPairTicker.observe(viewLifecycleOwner) { tickers ->
            updateTickerData(tickers)
        }

        fundingLiveTickerTradeViewModel.tradingPairTrade.observe(viewLifecycleOwner) { trades ->
            updateTradingData(trades)
        }
    }

    private fun getTradingPairFromArgument() {
        arguments?.getString(FundingItemListAdapter.FUNDING_ITEM).also {
            fundingLiveTickerTradeViewModel.fundingItem =
                arguments?.getString(FundingItemListAdapter.FUNDING_ITEM)
        } ?: findNavController().popBackStack()
    }

    private fun setupTickerRVAdapter() {
        rv_PairTicker.apply {
            adapter = TradingPairTickersAdapter()
        }
    }

    private fun updateTickerData(tradingPairTicker: TradingPairTicker) {

        (rv_PairTicker.adapter as TradingPairTickersAdapter).apply {
            addTradingPairTicker(tradingPairTicker)
            notifyItemInserted(0)
            rv_PairTicker.scrollToPosition(0)
        }
    }

    private fun setupTradingRVAdapter() {
        rv_PairTrade.apply {
            adapter = TradingPairTradesAdapter()
        }
    }

    private fun updateTradingData(tradingPairTrade: List<TradingPairTrade>) {

        (rv_PairTrade.adapter as TradingPairTradesAdapter).apply {
            addTradingPairTrades(tradingPairTrade)
            notifyItemInserted(0)
            rv_PairTrade.scrollToPosition(0)
        }
    }
}