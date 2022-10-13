package com.example.shop.data

import com.example.shop.data.database.dao.CategoryDao
import com.example.shop.data.database.dao.ProductDao
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShopRepository @Inject constructor(
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao,
    private val cartItems: MutableList<CartItem> = mutableListOf()
) {

    fun getCategories() = categoryDao.getCategories()

    fun getProducts() = productDao.getProducts()

    fun getCartItems(): Flow<List<CartItem>> {
        return flow {
            emit(cartItems)
        }
    }

    suspend fun addToCart(id: Int) {
        //if already in list add to quantity, if not add a new CartItem
        cartItems.firstOrNull { it.product.id == id }?.let {
            it.quantity++
        } ?: run {
            productDao.getProduct(id).collect {
                cartItems.add(
                    CartItem(it,1)
                )
            }
        }
    }

    fun removeFromCart(id: Int) {
        cartItems.first { it.product.id == id }.let {
            if(it.quantity > 1)
                it.quantity--
        }
    }

    fun deleteFromCart(id: Int) {
        cartItems.removeIf {
            it.product.id == id
        }
    }
}