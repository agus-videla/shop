package com.example.shop.data.database.callbacks

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shop.data.database.dao.CategoryDao
import com.example.shop.data.database.entities.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider

class CategoryCallback(
    private val provider: Provider<CategoryDao>,
) : RoomDatabase.Callback() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        Log.d("CATEGORY CALLBACK", "Populating Database")
        applicationScope.launch(Dispatchers.IO) {
            populateDatabase()
        }
    }

    private suspend fun populateDatabase() {
        var category = Category(1,"Jackets")
        provider.get().insertCategory(category)
        category = Category(2, "Trousers")
        provider.get().insertCategory(category)
    }
}