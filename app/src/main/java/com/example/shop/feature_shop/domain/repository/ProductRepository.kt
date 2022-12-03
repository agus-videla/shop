package com.example.shop.feature_shop.domain.repository

import com.example.shop.core.data.data_source.entities.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
    fun getProduct(idProduct: Int): Flow<Product>
}