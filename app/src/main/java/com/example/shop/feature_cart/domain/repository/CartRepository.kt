package com.example.shop.feature_cart.domain.repository

import com.example.shop.core.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getCartItems(): Flow<List<CartItem>>
    suspend fun createNewPendingCart(): Int
    suspend fun ensureCart(): Int
    suspend fun deleteFromCart(id: Int)
    suspend fun transferAnonymousCartToActiveUser()
    suspend fun getCartItem(cartId: Int, id: Int): CartItem?
    suspend fun updateCartItem(item: CartItem)
    suspend fun addCartItem(cartItem: CartItem)
}