package com.example.shop.feature_cart.domain.repository

import com.example.shop.core.data.data_source.entities.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getCartItems(): Flow<List<CartItem>>
    suspend fun addToCart(id: Int)
    suspend fun setCart(): Int
    suspend fun ensureCart(): Int
    suspend fun removeFromCart(id: Int)
    suspend fun deleteFromCart(id: Int)
    suspend fun transferAnonymousCartToActiveUser()
    suspend fun getCartItem(cartId: Int, id: Int): CartItem?
    suspend fun updateCartItem(item: CartItem)
    suspend fun addCartItem(cartItem: CartItem)
}