package com.example.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cryptoapp.data.database.AppDatabase
import io.reactivex.rxjava3.disposables.CompositeDisposable

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

//    fun getDetailInfo(fSym : String) : LiveData<CoinInfoDto> {
//        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
//    }

//    init {
//        loadData()
//    }

//    private fun loadData() {
//        val disposable = ApiFactory.apiService.getTopCoinsInfo(limit = 20)
//
//            .map { it.names?.map { it.coinName?.name }?.joinToString(",") as String }
//
//            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
//            .map { getPriceListFromRawData(it) }
//            .delaySubscription(50,TimeUnit.SECONDS)
//            .repeat()
//            .retry()
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                db.coinPriceInfoDao().insertPriceList(it)
//                Log.d("TEST_OF_LOADING_DATA", it.toString())
//
//            }, {
//                it.message?.let { it1 -> Log.d("TEST_OF_LOADING_DATA", it1) }
//            })
//        compositeDisposable.add(disposable)
//    }


//    private fun getPriceListFromRawData(
//        coinInfoJsonContainer: CoinInfoJsonContainerDto
//    ): List<CoinInfoDto> {
//        val result = ArrayList<CoinInfoDto>()
//        val jsonObject = coinInfoJsonContainer.json ?: return result
//        val coinKeySet = jsonObject.keySet()
//        for (coinKey in coinKeySet) {
//            val currencyJson = jsonObject.getAsJsonObject(coinKey)
//            val currencyKeySet = currencyJson.keySet()
//            for (currencyKey in currencyKeySet) {
//                val priceInfo = Gson().fromJson(
//                    currencyJson.getAsJsonObject(currencyKey),
//                    CoinInfoDto::class.java
//                )
//                result.add(priceInfo)
//            }
//        }
//        return result
//    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}