package com.example.shop.core.data.data_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shop.core.data.data_source.entities.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM Products")
    fun getProducts(): Flow<List<Product>>

    /*
    @Query("SELECT * FROM Products ORDER BY " +
           "CASE WHEN :sortBy = 'name'  AND :sort = 'DESC' THEN name END DESC, " +
           "CASE WHEN :sortBy = 'name'  AND :sort = 'ASC' THEN name END ASC, " +
           "CASE WHEN :sortBy = 'price'  AND :sort = 'DESC' THEN price END DESC, " +
           "CASE WHEN :sortBy = 'price'  AND :sort = 'ASC' THEN price END ASC, " +
           "CASE WHEN :sortBy = 'random'  AND :sort = 'DESC' THEN RANDOM () END DESC, " +
           "CASE WHEN :sortBy = 'random'  AND :sort = 'ASC' THEN RANDOM () END ASC")
    fun getProductsSortedBy(sortBy: String, sort: String = SortOrder.ASC.toString()): Flow<List<Product>>
     */
    @Query("SELECT * FROM Products WHERE :id == Products.id")
    fun getProduct(id: Int): Flow<Product>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: Product): Long

    @Query("DELETE FROM Products")
    suspend fun deleteAll()
}