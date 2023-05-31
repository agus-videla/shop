package com.example.shop.feature_cart.domain.use_case

import com.example.shop.feature_cart.domain.repository.CartRepository

class TransferCart(
    private val repository: CartRepository
) {
    suspend operator fun invoke() {
        repository.transferAnonymousCartToActiveUser()
    }
}
