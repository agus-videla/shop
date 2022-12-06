package com.example.shop.feature_cart.domain.use_case

import com.example.shop.feature_cart.util.CartItemWithProduct
import com.example.shop.feature_cart.domain.repository.CartRepository
import com.example.shop.feature_gondola.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class GetCartItems(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) {
    suspend operator fun invoke(): Flow<List<CartItemWithProduct>> {
        return cartRepository.getCartItems().map {
            it.map { cartItem ->
                CartItemWithProduct(productRepository.getProduct(cartItem.idProduct).first(),
                    cartItem.quantity)
            }
        }
    }
}
