package com.example.shop.data.database.callbacks

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
        super.onCreate(db)
        applicationScope.launch(Dispatchers.IO) {
            populateDatabase()
        }
    }

    private suspend fun populateDatabase() {
        provider.get().deleteAll()
        var category = Category(0,"Jackets")
        provider.get().insertCategory(category)
        category = Category(0, "Trousers")
        provider.get().insertCategory(category)
    }
}