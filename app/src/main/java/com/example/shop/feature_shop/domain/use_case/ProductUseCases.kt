package com.example.shop.feature_shop.domain.use_case

data class ProductUseCases(
    val getProducts: GetProducts,
    val getWishlist: GetWishlist,
    val toggleWished: ToggleWished
)
