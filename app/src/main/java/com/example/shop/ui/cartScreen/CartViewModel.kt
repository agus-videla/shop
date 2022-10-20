package com.example.shop.ui.cartScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.shop.data.CartItem
import com.example.shop.data.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: ShopRepository,
) : ViewModel() {

    private val _items = MutableStateFlow<List<CartItem>>(emptyList())
    val items get() =  _items.asStateFlow()

    init {
        getCartItems()
    }

    private fun getCartItems() {
        viewModelScope.launch {
            repository.getCartItems().collect {
                _items.emit(it)
            }
        }
    }

    fun addToCart(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToCart(id)
        }
        getCartItems()
    }

    fun removeFromCart(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFromCart(id)
        }
        getCartItems()
    }

    fun deleteFromCart(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromCart(id)
        }
        getCartItems()
    }

}