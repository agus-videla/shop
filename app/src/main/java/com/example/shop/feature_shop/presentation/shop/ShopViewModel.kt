package com.example.shop.feature_shop.presentation.shop

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.R
import com.example.shop.core.data.repository.ShopRepositoryImpl
import com.example.shop.feature_shop.domain.use_case.ProductUseCases
import com.example.shop.feature_shop.util.SortBy
import com.example.shop.feature_shop.util.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val repository: ShopRepositoryImpl,
    private val productUseCases: ProductUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(ShopState())
    val state: StateFlow<ShopState> get() = _state

    private var getProductsJob: Job? = null

    init {
        viewModelScope.launch {
            getWishlist()
            getProducts(SortBy.Name(SortOrder.Descending))
        }
    }

    private fun addToWishlist(productId: Int) {
        viewModelScope.launch {
            repository.addToWishlist(productId)
        }
    }

    private fun removeFromWishlist(productId: Int) {
        viewModelScope.launch {
            repository.removeFromWishlist(productId)
        }
    }

    suspend fun onEvent(event: ShopEvent) {
        when (event) {
            is ShopEvent.SortProducts -> {
                setSortIcon(event.sort)
                getProducts(event.sort)
            }
            is ShopEvent.AddToCart -> {
                addToCart(event.productId)
            }
            is ShopEvent.ToggleWish -> {
                productUseCases.toggleWished(event.productId)
            }
        }
    }

    private fun addToCart(id: Int) {
        viewModelScope.launch {
            repository.addToCart(id)
        }
    }

    private fun setSortIcon(sort: SortBy) {
        when (sort.sortOrder) {
            SortOrder.Ascending -> _state.value = _state.value.copy(sortIconId = R.drawable.ic_desc)
            SortOrder.Descending -> _state.value =
                _state.value.copy(sortIconId = R.drawable.ic_asc)
        }
    }

    private fun getProducts(productOrder: SortBy) {
        getProductsJob?.cancel()
        getProductsJob = productUseCases.getProducts(productOrder).onEach {
            _state.value = _state.value.copy(products = it)
        }.launchIn(viewModelScope)
        _state.value = _state.value.copy(productOrder = productOrder)
    }

    suspend fun getWishlist() {
        productUseCases.getWishlist().onEach {
            if (it.isEmpty())
                _state.value = _state.value.copy(wishlistVisibility = View.GONE)
            else
                _state.value = _state.value.copy(wishlistVisibility = View.VISIBLE)
            _state.value = _state.value.copy(wishlist = it)
        }.launchIn(viewModelScope)
    }

    suspend fun userIsLoggedIn(): Boolean = repository.userIsLoggedIn()

    fun isWished(id: Int): Boolean {
        return state.value.wishlist.any {
            it.id == id
        }
    }
}
