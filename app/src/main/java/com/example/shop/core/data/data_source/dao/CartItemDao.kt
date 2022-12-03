package com.example.shop.core.data.data_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shop.core.data.data_source.entities.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDao {
    @Query("SELECT * FROM CartItem WHERE cart_id = :cartId")
    fun getCartItems(cartId: Int): Flow<List<CartItem>>

    @Query("SELECT * FROM CartItem WHERE cart_id = :cartId AND product_id = :productId")
    fun getCartItem(cartId: Int, productId: Int): CartItem?

    @Query("DELETE FROM CartItem WHERE product_id = :productId")
    fun removeCartItem(productId: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCartItem(cartItem: CartItem): Long

    @Update
    suspend fun updateCartItem(cartItem: CartItem)

    @Query("DELETE FROM CartItem")
    fun removeAll()
}