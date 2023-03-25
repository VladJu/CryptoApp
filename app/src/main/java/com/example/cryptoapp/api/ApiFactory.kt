package com.example.cryptoapp.api

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//является синглтоном и содержит ссылку на ретрофит и она должны быть одна
object ApiFactory {

    private const val BASE_URL = "https://min-api.cryptocompare.com/data/"
    const val BASE_IMAGE_URL = "https://cryptocompare.com"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    //ретрофит создат реализацию для нашего интерфейса
    //это private переменная у которой есть только get и при вызвое его он вернет реализацию интефрейса
    val apiService: ApiService = retrofit.create(ApiService::class.java)

}