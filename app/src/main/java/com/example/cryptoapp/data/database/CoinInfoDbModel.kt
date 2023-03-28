package com.example.cryptoapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

//1) класс который являтся сущьнотью бд не должно быть никаких методов, это просто сущность БД
@Entity(tableName = "full_price_list")
data class CoinInfoDbModel(
    @PrimaryKey
    val fromSymbol: String,
    val toSymbol: String?,
    val price: Double?,
    val lastUpdate: Int?,
    val highDay: Double?,
    val lowDay: Double?,
    val lastMarket: String?,
    val imageUrl: String?
)