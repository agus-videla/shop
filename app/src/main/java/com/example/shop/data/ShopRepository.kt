package com.example.shop.data

import com.example.shop.data.database.dao.*
import com.example.shop.data.database.entities.*
import com.example.shop.ui.main.shop.SortOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ShopRepository @Inject constructor(
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao,
    private val userDao: UserDao,
    private val cartDao: CartDao,
    private val cartItemDao: CartItemDao,
    private val wishlistDao: WishlistDao,
) {
    companion object {
        const val ANONYMOUS_USER_ID = 1
    }

    private var activeUserId: Int = ANONYMOUS_USER_ID

    suspend fun getCartItems(): Flow<List<CartItem>> {
        return withContext(Dispatchers.IO) {
            val cartId = cartDao.getPendingCart(activeUserId)!!
            return@withContext cartItemDao.getCartItems(cartId)
        }
    }

    suspend fun addToCart(id: Int) {
        withContext(Dispatchers.IO) {
            val cartId = cartDao.getPendingCart(activeUserId)!!
            val cartItem = cartItemDao.getCartItem(cartId, id)
            cartItem?.let {
                it.quantity++
                cartItemDao.updateCartItem(it)
            } ?: run {
                cartItemDao.addCartItem(CartItem(id, cartId, 1))
            }
        }
    }

    suspend fun removeFromCart(id: Int) {
        val cartId = cartDao.getPendingCart(activeUserId)!!
        val cartItem = cartItemDao.getCartItem(cartId, id)
        cartItem?.let {
            if (it.quantity > 1) {
                it.quantity--
                cartItemDao.updateCartItem(it)
            }
        }
    }

    fun deleteFromCart(id: Int) {
        cartItemDao.removeCartItem(id)
    }

    fun getProductsSortedBy(selection: String, sortOrder: SortOrder): Flow<List<Product>> {
        var columnName = ""
        when (selection) {
            "Price" -> columnName = "price"
            "Name" -> columnName = "name"
            "Relevance" -> columnName = "random"
        }
        return productDao.getProductsSortedBy(columnName, sortOrder.toString())
    }

    suspend fun usernameAvailable(username: String): Boolean {
        return (userDao.usernameExists(username) == null)
    }

    suspend fun addUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.addUser(user)
        }
    }

    suspend fun isValid(username: String, password: String): Int? {
        return withContext(Dispatchers.IO) {
            return@withContext userDao.getUser(username, password)
        }
    }

    fun setActiveUser(id: Int) {
        activeUserId = id
    }

    suspend fun setCart() {
        withContext(Dispatchers.IO) {
            if (cartDao.getPendingCart(activeUserId) == null)
                cartDao.addCart(Cart(activeUserId, CartStatus.PENDING))
        }
    }

    fun getProduct(idProduct: Int): Flow<Product> {
        return productDao.getProduct(idProduct)
    }

    suspend fun wishlistItem(productId: Int) {
        withContext(Dispatchers.IO) {
            wishlistDao.addWishlistItem(WishlistItem(productId, activeUserId))
        }
    }
}