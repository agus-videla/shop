package com.example.shop.feature_gondola.domain.repository

import com.example.shop.core.data.data_source.entities.Product
import kotlinx.coroutines.flow.Flow

interface WishlistRepository {
    suspend fun addToWishlist(productId: Int)
    suspend fun removeFromWishlist(productId: Int)
    suspend fun isWished(productId: Int): Boolean
    suspend fun getWishlist(): Flow<List<Product>>
}