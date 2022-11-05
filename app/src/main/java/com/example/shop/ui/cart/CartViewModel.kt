package com.example.shop.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.data.ShopRepository
import com.example.shop.data.database.entities.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: ShopRepository,
) : ViewModel() {

    private val _items = MutableLiveData<List<CartItemWithProduct>>()
    val items: LiveData<List<CartItemWithProduct>> get() = _items

    init {
        getCartItems()
    }

    private fun getCartItems() {
        viewModelScope.launch {
                repository.getCartItems().map {
                    it.map { cartItem ->
                        async { CartItemWithProduct(repository.getProduct(cartItem.idProduct).first(), cartItem.quantity) }
                    }.awaitAll()
                }.collect {
                    _items.postValue(it)
            }
        }
    }

    fun addToCart(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToCart(id)
        }
    }

    fun removeFromCart(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFromCart(id)
        }
    }

    fun deleteFromCart(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromCart(id)
        }
    }
}

data class CartItemWithProduct(
    val product: Product,
    val quantity: Int
) {
    fun subTotal(): Long {
        return product.price * quantity
    }
}