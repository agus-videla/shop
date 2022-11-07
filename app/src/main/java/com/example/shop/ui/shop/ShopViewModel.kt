package com.example.shop.ui.shop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.data.ShopRepository
import com.example.shop.data.database.entities.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val repository: ShopRepository,
) : ViewModel() {
    private val sortBy = MutableLiveData<String>("Price")
    private val _sortOrder = MutableLiveData<SortOrder>(SortOrder.ASC)
    val sortOrder get() = _sortOrder
    private val _productList = MutableLiveData<List<Product>>()
    val products get() = _productList

    init {
        viewModelScope.launch {
            repository.setCart()
        }
        getProductsSorted()
    }

    fun addToCart(id: Int) {
        viewModelScope.launch {
            repository.addToCart(id)
        }
    }

    fun wishlistItem(productId: Int) {
        viewModelScope.launch {
            repository.wishlistItem(productId)
        }
    }


    fun changeSortOrder() {
        if(_sortOrder.value == SortOrder.ASC)
            _sortOrder.value = SortOrder.DESC
        else
            _sortOrder.value = SortOrder.ASC
        getProductsSorted()
    }

    fun sortBy(selection: String) {
        sortBy.value = selection
        getProductsSorted()
    }

    private fun getProductsSorted() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProductsSortedBy(sortBy.value ?: "Price", sortOrder.value ?: SortOrder.ASC)
                .collect {
                    _productList.postValue(it)
                }
        }
    }

}

enum class SortOrder {
    ASC , DESC
}
