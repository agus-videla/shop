package com.example.shop.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shop.data.database.dao.*
import com.example.shop.data.database.entities.*

@Database(entities = [Product::class, Category::class, User::class, Cart::class, CartItem::class, WishlistItem::class],
    version = 14,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class ShopDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao
    abstract fun cartItemDao(): CartItemDao
    abstract fun wishlistDao(): WishlistDao
}