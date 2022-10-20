package com.example.shop.data

import android.util.Log
import com.example.shop.data.database.dao.*
import com.example.shop.data.database.entities.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShopRepository @Inject constructor(
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao,
    private val userDao: UserDao,
    private val cartDao: CartDao,
    private val cartItemDao: CartItemDao,
    private val cartItems: MutableList<CartItem> = mutableListOf()
) {

    fun getCategories() = categoryDao.getCategories()

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

    fun getProductsSortedBy(selection: String, sortOrder: Boolean): Flow<List<Product>> {
        var columnName = ""
        when(selection) {
            "Price" -> columnName = "price"
            "Name" -> columnName = "name"
            "Relevance" -> columnName = "random"
        }
        return productDao.getProductsSortedBy(columnName, sortOrder)
    }

    suspend fun usernameAvailable(username: String): Boolean {
        return (userDao.usernameExists(username) == null)
    }
}