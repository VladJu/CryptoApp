package com.example.cryptoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_coin_info.view.*

class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    //коллекция объектов которые надо отображать
    var coinInfoList: List<CoinPriceInfo> = listOf()
        //когда переменной будем присваивать новое значение мы хотим вызывать метод notifyDataSetChanged(), поэтому переопределям сеттер для этой переменной
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    // 2 переменная котрой можно присвоить значение из активити
    var onCoinClickListener : OnCoinClickListener?=null

    //Создаем view из макета
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_coin_info,
            parent,
            false
        )
        return CoinInfoViewHolder(view)
    }

    //по номеру позиции показвыает какой элемент нам надо сейчас отобразить( и какой текст,цвет там установить)
    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        //устанавилваем значения нужным нам элеентам
        with(holder) {
            with(coin) {
                val symbolsTemplate = context.resources.getString(R.string.symbols_template)
                val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
                tvSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                tvPrice.text = price.toString()
                tvLastTimeUpdate.text = String.format(lastUpdateTemplate, getFormattedTime())
                Picasso.get().load(getFullImageUrl()).into(ivLogoCoin)
                // 3 при клике на itemView будет првоерено занчение !=0 .onCoinClick(coin)
                itemView.setOnClickListener{
                    onCoinClickListener?.onCoinClick(coin)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return coinInfoList.size
    }

    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivLogoCoin = itemView.ivLogoCoin
        val tvSymbols = itemView.tvSymbols
        val tvPrice = itemView.tvPrice
        val tvLastTimeUpdate = itemView.tvLastTimeUpdate

    }
    // 1
    interface OnCoinClickListener{
        fun  onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}