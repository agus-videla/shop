package com.example.shop.feature_gondola.domain.repository

import com.example.shop.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
    fun getProduct(idProduct: Int): Flow<Product>
}