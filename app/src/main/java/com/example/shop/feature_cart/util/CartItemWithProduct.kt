package com.example.shop.feature_cart.util

import com.example.shop.core.data.data_source.entities.Product

data class CartItemWithProduct(
    val product: Product,
    val quantity: Int,
) {
    fun subTotal(): Long {
        return product.price * quantity
    }
}
