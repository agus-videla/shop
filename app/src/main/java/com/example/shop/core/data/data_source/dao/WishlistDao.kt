package com.example.shop.core.data.data_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shop.core.domain.model.Product
import com.example.shop.core.domain.model.WishlistItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {
    @Query("SELECT Products.id, Products.name, Products.price, Products.stock, Products.description, Products.thumbnail, Products.category_id " +
            "FROM WishlistItem INNER JOIN Products ON WishlistItem.product_id = Products.id " +
            "WHERE WishlistItem.user_id = :userId")
    fun getWishlist(userId: Int): Flow<List<Product>>

    @Query("SELECT product_id FROM WishlistItem WHERE product_id = :productId AND user_id = :userId")
    fun isWished(productId: Int, userId: Int): Long?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToWishlist(wishlistItem: WishlistItem): Long

    @Query("DELETE FROM WishlistItem WHERE user_id = :userId AND product_id = :productId")
    fun removeFromWishlist(userId: Int, productId: Int)
}