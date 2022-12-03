package com.example.shop.core.data.data_source.callbacks

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shop.core.data.data_source.dao.CartItemDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider

class CartItemCallback(
    private val provider: Provider<CartItemDao>,
) : RoomDatabase.Callback() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        Log.d("CARTITEM CALLBACK", "Deleting CartItems")
        applicationScope.launch(Dispatchers.IO) {
            delete()
        }
    }

    private fun delete() {
        provider.get().removeAll()
    }
}