package com.example.cryptoapp.data.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class  CoinDataWorkerFactory @Inject constructor(
    //в кач key = имя класса , value = сам воркер
    private val workerProviders: @JvmSuppressWildcards Map<Class<out ListenableWorker>, Provider<ChildWorkerFactory>>
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        //в зависиомсти от имени класса будет получать Воркер из Коллекции
        //в отличии от VMFactory прилетает не сам класс, а его название в виде строки
        // поэтому надо явно проверять название классаи в зависимости от этого возвращать разные
        // экземпляры воркеров
        return when(workerClassName){
            //qualifiedName - чтобы поулчить полное название класса
            RefreshDataWorker::class.qualifiedName-> {
              val childWorkerFactory=  workerProviders[RefreshDataWorker::class.java]?.get()
                // и из нее получаем экземпляр воркера
                return childWorkerFactory?.create(appContext,workerParameters)
            }
            else -> null
        }
    }
}