package com.example.shop.feature_gondola.presentation

import com.example.shop.feature_gondola.util.SortBy

sealed class GondolaEvent {
    data class SortProducts(val sort: SortBy) : GondolaEvent()
    data class AddToCart(val productId: Int) : GondolaEvent()
    data class ToggleWish(val productId: Int) : GondolaEvent()
    object IsUserLoggedIn : GondolaEvent()
}