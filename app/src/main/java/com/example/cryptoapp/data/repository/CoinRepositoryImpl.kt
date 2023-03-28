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
            //2
            //добавялем tru/catch чтобы когда запустим приложение без интернета, то метод который
            //обращается к сети вернет ошибку и мы ее обработаем чтбы не было краша
            //Еслиу нас не будет интерента, упадем в блок catch (ничего выполненно не будет,
            //устанвоится задержка и через 10 сек запрос повториться
            try {
                val topCoins = apiService.getTopCoinsInfo(limit = 25)
                val fSyms = mapper.mapNamesLstToString(topCoins)
                val jsonContainerDto = apiService.getFullPriceList(fSyms = fSyms)
                val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainerDto)
                val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
                coinInfoDao.insertPriceList(dbModelList)
            } catch (e: Exception) {
            }
            delay(10000)
        }
    }
}