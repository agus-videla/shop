package com.example.shop.feature_cart.domain.use_case

import com.example.shop.core.data.data_source.entities.CartItem
import com.example.shop.feature_cart.domain.repository.CartRepository

class AddToCart(
    private val repository: CartRepository
) {
    suspend operator fun invoke(productId: Int) {
        repository.addToCart(productId)
        val cartId: Int = repository.ensureCart()
        val cartItem = repository.getCartItem(cartId, productId)
        cartItem?.let {
            it.quantity++
            repository.updateCartItem(it)
        } ?: run {
            repository.addCartItem(CartItem(productId, cartId, 1))
        }
    }
}