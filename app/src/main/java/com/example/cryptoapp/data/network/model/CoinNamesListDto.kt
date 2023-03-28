package com.example.cryptoapp.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoinNamesListDto {
    @SerializedName("Data")
    @Expose
    //лежит коллекция имен но каждое заключено в контейнер
    val names: List<CoinNameContainerDto>? = null
}