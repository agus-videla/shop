package com.example.shop.feature_cart.presentation

import com.example.shop.feature_cart.domain.use_case.CartItemWithProduct

data class CartState(
    var cartItems: List<CartItemWithProduct> = emptyList()
)
