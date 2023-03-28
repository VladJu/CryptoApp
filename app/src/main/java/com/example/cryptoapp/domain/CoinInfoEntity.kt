package com.example.cryptoapp.domain

data class CoinInfoEntity(
    val fromSymbol: String,
    val toSymbol: String? = null,
    val price: Double? = null,
    val lastUpdate: Int? = null,
    val highDay: Double? = null,
    val lowDay: Double? = null,
    val lastMarket: String? = null,
    val imageUrl: String?
)