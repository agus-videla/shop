package com.example.shop.core.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shop.core.data.data_source.dao.*
import com.example.shop.core.data.data_source.entities.*
import com.example.shop.core.domain.model.*

@Database(entities = [Product::class, Category::class, User::class, Cart::class, CartItem::class, WishlistItem::class],
    version = 15,
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