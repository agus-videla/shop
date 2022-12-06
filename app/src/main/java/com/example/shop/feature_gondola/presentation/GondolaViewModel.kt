package com.example.shop.feature_gondola.presentation

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.R
import com.example.shop.core.data.repository.ShopRepositoryImpl
import com.example.shop.feature_gondola.domain.use_case.ProductUseCases
import com.example.shop.feature_gondola.util.SortBy
import com.example.shop.feature_gondola.util.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GondolaViewModel @Inject constructor(
    private val repository: ShopRepositoryImpl,
    private val productUseCases: ProductUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(GondolaState())
    val state: StateFlow<GondolaState> get() = _state

    private var getProductsJob: Job? = null
    private var getWishlistJob: Job? = null

    init {
        viewModelScope.launch {
            getWishlist()
            getProducts(SortBy.Name(SortOrder.Descending))
        }
    }

    suspend fun onEvent(event: GondolaEvent) {
        when (event) {
            is GondolaEvent.SortProducts -> {
                setSortIcon(event.sort)
                getProducts(event.sort)
            }
            is GondolaEvent.AddToCart -> {
                productUseCases.addToCart(event.productId)
            }
            is GondolaEvent.ToggleWish -> {
                productUseCases.toggleWished(event.productId)
            }
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
        getWishlistJob?.cancel()
        getWishlistJob = productUseCases.getWishlist().onEach {
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
