package com.example.shop.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shop.data.database.entities.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM Products")
    fun getProducts(): Flow<List<Product>>

    @Query("SELECT * FROM Products ORDER BY " +
           "CASE WHEN :sortBy = 'name'  AND :sort = 0 THEN name END DESC, " +
           "CASE WHEN :sortBy = 'name'  AND :sort = 1 THEN name END ASC, " +
           "CASE WHEN :sortBy = 'price'  AND :sort = 0 THEN price END DESC, " +
           "CASE WHEN :sortBy = 'price'  AND :sort = 1 THEN price END ASC, " +
           "CASE WHEN :sortBy = 'random'  AND :sort = 0 THEN RANDOM () END DESC, " +
           "CASE WHEN :sortBy = 'random'  AND :sort = 1 THEN RANDOM () END ASC")
    fun getProductsSortedBy(sortBy: String, sort: Boolean = false): Flow<List<Product>>

    @Query("SELECT * FROM Products WHERE :id == Products.id")
    fun getProduct(id: Int): Flow<Product>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: Product): Long

    @Query("DELETE FROM Products")
    suspend fun deleteAll()
}