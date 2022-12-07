package com.example.shop.feature_cart.domain.use_case

import com.example.shop.core.domain.use_case.IsUserLoggedIn

data class CartUseCases(
    val addToCart: AddToCart,
    val removeFromCart: RemoveFromCart,
    val deleteFromCart: DeleteFromCart,
    val transferCart: TransferCart,
    val getCartItems: GetCartItems,
    val isUserLoggedIn: IsUserLoggedIn
)
