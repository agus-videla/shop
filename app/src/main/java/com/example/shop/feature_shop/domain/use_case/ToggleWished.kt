package com.example.shop.feature_shop.domain.use_case

import com.example.shop.feature_shop.domain.repository.WishlistRepository

class ToggleWished(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke(productId: Int) {
        if(repository.isWished(productId))
            repository.removeFromWishlist(productId)
        else
            repository.addToWishlist(productId)
    }
}