package com.example.shop.feature_authentication.data

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.shop.core.data.datastore.DataStoreManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class LogOutWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val dataSoreManager: DataStoreManager
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        dataSoreManager.clearActiveUser()
        return Result.success()
    }
}