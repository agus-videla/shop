package com.example.shop.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shop.data.database.entities.Product
import com.example.shop.data.database.entities.WishlistItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {
    @Query("SELECT Products.id, Products.name, Products.price, Products.stock, Products.description, Products.thumbnail, Products.category_id " +
            "FROM WishlistItem INNER JOIN Products ON WishlistItem.product_id = Products.id " +
            "WHERE WishlistItem.user_id = :userId")
    fun getWishlist(userId: Int): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToWishlist(wishlistItem: WishlistItem): Long

    @Query("DELETE FROM WishlistItem WHERE user_id = :userId AND product_id = :productId")
    suspend fun removeFromWishlist(userId: Int, productId: Int)
}