package com.example.shop.feature_cart.domain.use_case

data class CartUseCases(
    val addToCart: AddToCart,
    val removeFromCart: RemoveFromCart,
    val deleteFromCart: DeleteFromCart,
    val transferCart: TransferCart,
    val getCartItems: GetCartItems
)
