package com.example.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.domain.CoinInfoEntity
import com.example.cryptoapp.domain.CoinRepository
import kotlinx.coroutines.delay

class CoinRepositoryImpl(private val application: Application) : CoinRepository {

    private val coinInfoDao=AppDatabase.getInstance(application).coinPriceInfoDao()
    private val mapper=CoinMapper()
    private val apiService=ApiFactory.apiService

    override fun getCoinInfoList(): LiveData<List<CoinInfoEntity>> {
        return Transformations.map(coinInfoDao.getPriceList()){
            it.map { mapper.mapDbModelToEntity(it) }
        }
    }

    override fun getCoinDetailInfo(fromSymbols: String): LiveData<CoinInfoEntity> {
        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fromSymbols)){
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true) {
            //1-получаем то популярных валют
            val topCoins = apiService.getTopCoinsInfo(limit = 25)
            //2- преобразоываваем все валюты в 1 строку
            val fSyms = mapper.mapNamesLstToString(topCoins)
            //по этой строке загружаем все необходимые данные из сети
            val jsonContainerDto = apiService.getFullPriceList(fSyms = fSyms)
            //3-преобразоываваем джсонконтейнер в коллекцию объектов Dto
            val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainerDto)
            //4- коллецию объектов Dto преобразуем в коллекцию DbModel
            val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
            //5-вставляем данные в бд
            coinInfoDao.insertPriceList(dbModelList)
            delay(10000)
        }
    }
}