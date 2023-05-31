package com.example.shop.core.data.data_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shop.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM Products")
    fun getProducts(): Flow<List<Product>>

    @Query("SELECT * FROM Products WHERE :id == Products.id")
    suspend fun getProduct(id: Int): Product

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: Product): Long

    @Query("DELETE FROM Products")
    suspend fun deleteAll()
}