package com.example.cryptoapp.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//является синглтоном и содержит ссылку на ретрофит и она должны быть одна
object ApiFactory {

    private const val BASE_URL = "https://min-api.cryptocompare.com/data/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    //ретрофит создат реализацию для нашего интерфейса
    //это private переменная у которой есть только get и при вызвое его он вернет реализацию интефрейса
    val apiService: ApiService = retrofit.create(ApiService::class.java)

}