package com.example.shop.data.database.callbacks

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shop.data.database.dao.CartDao
import com.example.shop.data.database.dao.CartItemDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider

class CartCallback(
    private val provider: Provider<CartDao>,
) : RoomDatabase.Callback() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        Log.d("CART CALLBACK", "Deleting Carts")
        applicationScope.launch(Dispatchers.IO) {
            delete()
        }
    }

    private fun delete() {
        provider.get().removeAll()
    }
}

