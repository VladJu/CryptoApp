package com.example.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_info_about_coin.*

class DetailCoinActivity : AppCompatActivity() {
    private lateinit var viewModel: CoinViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_info_about_coin)
        //Если не содержит такой ключ выходим из активити
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol =intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProvider
            .AndroidViewModelFactory(application)
            .create(CoinViewModel::class.java)
        if (fromSymbol != null) {
            viewModel.getDetailInfo(fromSymbol).observe(this) {
                tvPrice.text=it.price.toString()
                tvMinPrice.text=it.lowDay.toString()
                tvMaxPrice.text=it.highDay.toString()
                tvLastMarket.text=it.lastMarket
                tvUpdate.text=it.getFormattedTime()
                tvFromSymbol.text=it.fromSymbol
                tvToSymbol.text=it.toSymbol
                Picasso.get().load(it.getFullImageUrl()).into(ivLogoCoin)
            }
        }
    }

    companion object {
       private const val EXTRA_FROM_SYMBOL = "fSym"
        //всегда когда передаем параметры из 1 активити в другую использовать такой способ инкапсулировать данные
        //чтобы CoinPriceInfoActivity ничего не занала о существовании каких нибудь ключей
        fun newIntent(context: Context, fromSymbol : String) : Intent {
            val intent = Intent(context, DetailCoinActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL,fromSymbol)
            return intent
        }
    }
}