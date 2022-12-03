package com.example.shop.core.data.data_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shop.core.data.data_source.entities.Cart

@Dao
interface CartDao {
    @Query("SELECT id FROM Cart WHERE status = 'PENDING' AND user_id = :userId")
    fun getPendingCart(userId: Int): Int?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCart(cart: Cart): Long

    @Query("UPDATE Cart SET user_id = :userId WHERE user_id = 1")
    suspend fun transferCart(userId: Int)

    @Query("UPDATE Cart SET status = 'CANCELLED' WHERE user_id = :userId AND status = 'PENDING'")
    suspend fun cancelCart(userId: Int)

    @Query("DELETE FROM Cart")
    fun removeAll()
}