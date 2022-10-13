package com.example.shop.ui.cartScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.data.CartItem
import com.example.shop.data.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: ShopRepository,
) : ViewModel() {

    private val _items = MutableLiveData<List<CartItem>>()
    val items get() = _items

    init {
        viewModelScope.launch {
            repository.getCartItems().collect {
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