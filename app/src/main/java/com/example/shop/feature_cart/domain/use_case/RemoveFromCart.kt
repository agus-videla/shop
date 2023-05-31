package com.example.shop.feature_cart.domain.use_case

import com.example.shop.feature_cart.domain.repository.CartRepository

class RemoveFromCart(
    private val repository: CartRepository
) {
    suspend operator fun invoke(productId: Int) {
        val cartId = repository.ensureCart()
        val cartItem = repository.getCartItem(cartId, productId)
        cartItem?.let {
            if (it.quantity > 1) {
                it.quantity--
                repository.updateCartItem(it)
            }
        }
    }
}