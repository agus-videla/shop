package com.example.shop.feature_authentication.data

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.shop.core.data.datastore.DataStoreManager
import com.example.shop.core.presentation.StartActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class LogOutWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val dataSoreManager: DataStoreManager,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        dataSoreManager.clearActiveUser()
        applicationContext.mainExecutor.execute {
            val intent = Intent(applicationContext, StartActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("SHOW_DIALOG", true)
            startActivity(applicationContext, intent, null)
        }
        return Result.success()
    }
}