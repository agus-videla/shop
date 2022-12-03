package com.example.shop.feature_shop.presentation.shop

import com.example.shop.feature_shop.util.SortBy

sealed class ShopEvent {
    data class SortProducts(val sort: SortBy) : ShopEvent()
    data class AddToCart(val productId: Int) : ShopEvent()
    data class ToggleWish(val productId: Int) : ShopEvent()
}