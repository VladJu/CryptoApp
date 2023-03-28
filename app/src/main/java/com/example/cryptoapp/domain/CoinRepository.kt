package com.example.cryptoapp.domain

import androidx.lifecycle.LiveData

interface CoinRepository {

    fun getCoinInfoList(): LiveData<List<CoinInfoEntity>>

    fun getCoinDetailInfo(fromSymbols: String): LiveData<CoinInfoEntity>

    suspend fun loadData()

}