package com.example.shop.feature_gondola.domain.use_case

import com.example.shop.core.data.data_source.entities.Product
import com.example.shop.feature_gondola.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow

class GetWishlist(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke(): Flow<List<Product>> {
        return repository.getWishlist()
    }
}