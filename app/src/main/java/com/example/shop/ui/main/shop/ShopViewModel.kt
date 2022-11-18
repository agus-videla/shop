package com.example.shop.ui.main.shop

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
    private val _wishlist = MutableLiveData<List<Product>>()
    val wishlist get() = _wishlist

    init {
        viewModelScope.launch {
            getWishlist()
        }
    }

    suspend fun getWishlist() {
        repository.getWishlist().collect {
            _wishlist.postValue(it)
        }
    }

    fun addToCart(id: Int) {
        viewModelScope.launch {
            repository.addToCart(id)
        }
    }

    fun addToWishlist(productId: Int) {
        viewModelScope.launch {
            repository.addToWishlist(productId)
        }
    }

    fun removeFromWishlist(productId: Int) {
        viewModelScope.launch {
            repository.removeFromWishlist(productId)
        }
    }

    fun changeSortOrder() {
        if (_sortOrder.value == SortOrder.ASC)
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
            repository.getProductsSortedBy(sortBy.value ?: "Price",
                sortOrder.value ?: SortOrder.ASC)
                .collect {
                    _productList.postValue(it)
                }
        }
    }

    suspend fun userIsLoggedIn(): Boolean = repository.userIsLoggedIn()

    fun isWished(id: Int) : Boolean {
        return wishlist.value?.any {
            it.id == id
        } ?: false
    }
}

enum class SortOrder {
    ASC, DESC
}
