package com.example.shop.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shop.data.database.entities.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDao {
    @Query("SELECT * FROM CartItem WHERE cart_id = :cartId")
    fun getCartItems(cartId: Int): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCartItem(cartItem: CartItem): Long

    @Update
    suspend fun updateCartItem(cartItem: CartItem)
}