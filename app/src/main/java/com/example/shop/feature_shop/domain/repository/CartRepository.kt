package com.example.shop.feature_shop.domain.repository

import com.example.shop.core.data.data_source.entities.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getCartItems(): Flow<List<CartItem>>
    suspend fun addToCart(id: Int)
    suspend fun setCart(): Int
    suspend fun ensureCart(): Int
    suspend fun removeFromCart(id: Int)
    fun deleteFromCart(id: Int)
    suspend fun transferAnonymousCartToActiveUser()
}