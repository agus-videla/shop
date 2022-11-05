package com.example.shop.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shop.data.database.entities.Cart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT id FROM Cart WHERE status == 'PENDING' AND user_id == :userId")
    fun getPendingCart(userId: Int): Int?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCart(cart: Cart): Long

    @Update
    suspend fun updateCart(cart: Cart)

    @Query("DELETE FROM Cart")
    fun removeAll()
}