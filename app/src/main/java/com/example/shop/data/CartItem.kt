package com.example.shop.data

import com.example.shop.data.database.entities.Product

data class CartItem(
    val product: Product,
    var quantity: Int,
){
    fun subTotal(): Long {
        return product.price * quantity
    }
}
