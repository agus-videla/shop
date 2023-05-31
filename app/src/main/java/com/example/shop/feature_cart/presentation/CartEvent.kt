package com.example.shop.feature_cart.presentation

sealed class CartEvent {
    data class AddToCart(val productId: Int): CartEvent()
    data class RemoveFromCart(val productId: Int): CartEvent()
    data class DeleteFromCart(val productId: Int): CartEvent()
    object IsUserLoggedIn : CartEvent()
    object TransferCart : CartEvent()
}
