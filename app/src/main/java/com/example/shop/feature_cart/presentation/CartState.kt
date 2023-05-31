package com.example.shop.feature_cart.presentation

import com.example.shop.feature_cart.util.CartItemWithProduct

data class CartState(
    var userIsLoggedIn: Boolean? = null,
    var cartItems: List<CartItemWithProduct> = emptyList()
)
