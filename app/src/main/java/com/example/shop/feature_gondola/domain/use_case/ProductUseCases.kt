package com.example.shop.feature_gondola.domain.use_case

import com.example.shop.feature_cart.domain.use_case.AddToCart

data class ProductUseCases(
    val getProducts: GetProducts,
    val getWishlist: GetWishlist,
    val toggleWished: ToggleWished,
    val addToCart: AddToCart
)
