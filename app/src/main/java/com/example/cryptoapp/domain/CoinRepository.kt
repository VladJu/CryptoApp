package com.example.cryptoapp.domain

import androidx.lifecycle.LiveData

//2)
interface CoinRepository {

    fun getCoinInfoList() : LiveData<List<CoinInfo>>

    fun getCoinDetailInfo(fromSymbols: String) : LiveData<CoinInfo>

}