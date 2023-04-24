package com.example.cryptoapp.data.workers

import android.content.Context
import androidx.work.*
import com.example.cryptoapp.data.database.CoinInfoDao
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class RefreshDataWorker (
    context: Context,
    workerParameters: WorkerParameters,
    private val coinInfoDao: CoinInfoDao,
    private val mapper: CoinMapper,
    private val apiService: ApiService
) : CoroutineWorker(context, workerParameters) {


    override suspend fun doWork(): Result {
        while (true) {
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

    companion object {

        const val WORKER_NAME = "RefreshDataWorker"
        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }

    class Factory @Inject constructor(
        private val coinInfoDao: CoinInfoDao,
        private val mapper: CoinMapper,
        private val apiService: ApiService
    ) : ChildWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return RefreshDataWorker(
                context,
                workerParameters,
                coinInfoDao,
                mapper,
                apiService
            )
        }
    }
}