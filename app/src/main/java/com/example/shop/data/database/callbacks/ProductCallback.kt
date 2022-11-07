package com.example.shop.data.database.callbacks

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shop.data.database.dao.ProductDao
import com.example.shop.data.database.entities.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider

class ProductCallback(
    private val provider: Provider<ProductDao>,
) : RoomDatabase.Callback() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        Log.d("PRODUCT CALLBACK", "Populating Database")
        applicationScope.launch(Dispatchers.IO) {
            populateDatabase()
        }
    }

    private suspend fun populateDatabase() {
        var product = Product(
            1,
            "Mountain Jacket",
            50000L,
            10,
            "winter jacket",
            "some.url",
            1
        )
        provider.get().insertProduct(product)
        product = Product(
            2,
            "Winter Sweater",
            3000L,
            20,
            "winter sweater",
            "some.url",
            1
        )
        provider.get().insertProduct(product)
        product = Product(
            3,
            "Jean",
            2000L,
            30,
            "Just some ordinary Jeans",
            "some.url",
            2
        )
        provider.get().insertProduct(product)
        product = Product(
            4,
            "Shorts",
            1500L,
            30,
            "Breezy shorts",
            "some.url",
            2
        )
        provider.get().insertProduct(product)
    }
}