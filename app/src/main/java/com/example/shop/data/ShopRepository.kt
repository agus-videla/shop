package com.example.shop.data

import com.example.shop.data.database.dao.*
import com.example.shop.data.database.entities.*
import com.example.shop.data.datastore.DataStoreManager
import com.example.shop.data.datastore.DataStoreManager.Companion.ANONYMOUS_USER_ID
import com.example.shop.ui.main.shop.SortOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ShopRepository @Inject constructor(
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao,
    private val userDao: UserDao,
    private val cartDao: CartDao,
    private val cartItemDao: CartItemDao,
    private val wishlistDao: WishlistDao,
    private val dataStoreManager: DataStoreManager,
) {
    suspend fun getCartItems(): Flow<List<CartItem>> {
        return withContext(Dispatchers.IO) {
            val cartId = ensureCart()
            return@withContext cartItemDao.getCartItems(cartId)
        }
    }

    suspend fun addToCart(id: Int) {
        withContext(Dispatchers.IO) {
            val cartId: Int = ensureCart()
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
        val cartId = ensureCart()
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

    suspend fun getUserIdIfExists(username: String, password: String): Int? {
        return withContext(Dispatchers.IO) {
            return@withContext userDao.getUser(username, password)
        }
    }

    suspend fun setActiveUser(id: Int) {
        dataStoreManager.setActiveUser(id)
    }

    private suspend fun setCart(): Int {
        return withContext(Dispatchers.IO) {
            cartDao.addCart(Cart(dataStoreManager.getActiveUser(), CartStatus.PENDING)).toInt()
        }
    }

    private suspend fun ensureCart(): Int {
        cartDao.getPendingCart(dataStoreManager.getActiveUser())?.let {
            return it
        } ?: run {
            return setCart()
        }
    }

    fun getProduct(idProduct: Int): Flow<Product> {
        return productDao.getProduct(idProduct)
    }

    suspend fun addToWishlist(productId: Int) {
        withContext(Dispatchers.IO) {
            wishlistDao.addToWishlist(WishlistItem(productId, dataStoreManager.getActiveUser()))
        }
    }

    suspend fun removeFromWishlist(productId: Int) {
        withContext(Dispatchers.IO) {
            wishlistDao.removeFromWishlist(dataStoreManager.getActiveUser(), productId)
        }
    }

    suspend fun userIsLoggedIn(): Boolean {
        return dataStoreManager.getActiveUser() != ANONYMOUS_USER_ID
    }

    suspend fun transferAnonymousCartToActiveUser() {
        withContext(Dispatchers.IO) {
            cartDao.cancelCart(dataStoreManager.getActiveUser())
            cartDao.transferCart(dataStoreManager.getActiveUser())
        }
    }

    suspend fun getWishlist(): Flow<List<Product>> {
        return withContext(Dispatchers.IO) {
            wishlistDao.getWishlist(dataStoreManager.getActiveUser())
        }
    }

    suspend fun logOut() {
        dataStoreManager.clearActiveUser()
    }
}