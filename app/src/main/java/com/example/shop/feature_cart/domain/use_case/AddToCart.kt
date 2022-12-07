package com.example.shop.feature_cart.domain.use_case

import com.example.shop.core.domain.model.CartItem
import com.example.shop.feature_cart.domain.repository.CartRepository

class AddToCart(
    private val repository: CartRepository
) {
    suspend operator fun invoke(productId: Int) {
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