package com.example.shop.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shop.data.database.dao.CategoryDao
import com.example.shop.data.database.dao.ProductDao
import com.example.shop.data.database.entities.Category
import com.example.shop.data.database.entities.Product

@Database(
    entities = [Product::class, Category::class],
    version = 6,
    exportSchema = false
)
abstract class ShopDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao

}