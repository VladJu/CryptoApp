package com.example.cryptoapp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.model.CoinPriceInfo
import com.example.cryptoapp.data.model.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    fun getDetailInfo(fSym : String) : LiveData<CoinPriceInfo> {
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }

    init {
        loadData()
    }

    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo(limit = 20)
            // помощью(joinToString - превращает всю коллекцию в 1 строку и каждый элемент разделит
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") as String }
            //возьмет полученную строку и передаст строку внтрь блока{} в виде it и загрузим
                //всю инфо о валютах
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRawData(it) }
            .delaySubscription(50,TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                Log.d("TEST_OF_LOADING_DATA", it.toString())

            }, {
                it.message?.let { it1 -> Log.d("TEST_OF_LOADING_DATA", it1) }
            })
        compositeDisposable.add(disposable)
    }

    //метод будет преобразовывать CoinPriceInfoRawData в лист CoinPriceInfo
    //получаем CoinPriceInfo из CoinPriceInfoRawData
    private fun getPriceListFromRawData( // на вход приходить объект CoinPriceInfoRawData - который
        //содержит jsonObject и джон мы парсим в ручную (в нем есть ключь BTC по которому находиться
        //другой jsonObject -> в другом джсонобъекте по ключу USD мы можем получить нужные нам данные
        coinPriceInfoRawData: CoinPriceInfoRawData
    ): List<CoinPriceInfo> {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result
        //1 берем все ключи (BTC,ETH и тд все монеты которые передали)
        val coinKeySet = jsonObject.keySet()
        //2проходимся по ним и получаем все вложеные джсонобъекты
        for (coinKey in coinKeySet) {
            //3получили вложенный jsonObject и каждый из этих вложенных джсонов содержит
            //только 1 ключь USD
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            //теперь из объекта выше получаем нужный нам объект как CoinPriceInfo
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                //4 по этому ключу получаем jsonObject и при помощи библиотеки Gson конвектируем его
                //в нужный нам класс CoinPriceInfo
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}