package com.example.cryptoapp.data.network

import com.example.cryptoapp.data.model.CoinInfoListOfData
import com.example.cryptoapp.data.model.CoinPriceInfoRawData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

//хранятся все методы по работе с сетью
interface ApiService {

    //получаем список самых популярных валют
    @GET("top/totalvolfull") //вставляем эндпойнт
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY
    ): Single<CoinInfoListOfData>

    //получаем полную инфо о ценах
    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_FROM_SYMBOlS) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY
    ): Single<CoinPriceInfoRawData>

    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"


        private const val QUERY_PARAM_FROM_SYMBOlS = "fsyms"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"

        private const val CURRENCY = "USD"
    }

}