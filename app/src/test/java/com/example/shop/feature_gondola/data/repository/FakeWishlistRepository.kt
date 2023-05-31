package com.example.shop.feature_gondola.data.repository

import com.example.shop.core.domain.model.Product
import com.example.shop.core.domain.model.WishlistItem
import com.example.shop.feature_gondola.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWishlistRepository: WishlistRepository {
    private val userId = 0
    private val wishlist = mutableListOf<WishlistItem>()

    override suspend fun addToWishlist(productId: Int) {
        wishlist.add(WishlistItem(productId, userId))
    }

    override suspend fun removeFromWishlist(productId: Int) {
        wishlist.removeIf { it.idProduct == productId }
    }

    override suspend fun isWished(productId: Int): Boolean {
        return (wishlist.find { it.idProduct == productId }) != null
    }

    override suspend fun getWishlist(): Flow<List<Product>> {
        val products = mutableListOf<Product>()
        for(wish in wishlist) {
            products.add(
                Product(
                    id = wish.idProduct,
                    name = "stuff",
                    price = 9999,
                    stock = 0,
                    description = "thing",
                    thumbnail = "thumb",
                    idCategory = 1
                )
            )
        }
        return flow {
            emit(products.toList())
        }
    }
}