package com.example.cryptoapp.data.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

//Child - потмоу что он создает не все воркеры, а только каждый конкретный экземлпяр в данном случае
//RefreshDataWorker
interface ChildWorkerFactory {

    fun create(
        context: Context,
        workerParameters: WorkerParameters
    ) : ListenableWorker
}