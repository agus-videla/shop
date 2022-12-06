package com.example.shop.core.data.repository

import com.example.shop.core.data.data_source.dao.*
import com.example.shop.core.data.data_source.entities.*
import com.example.shop.core.data.datastore.DataStoreManager
import com.example.shop.core.data.datastore.DataStoreManager.Companion.ANONYMOUS_USER_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ShopRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao,
    private val userDao: UserDao,
    private val cartDao: CartDao,
    private val cartItemDao: CartItemDao,
    private val wishlistDao: WishlistDao,
    private val dataStoreManager: DataStoreManager,
) : ShopRepository {

    override suspend fun getCartItems(): Flow<List<CartItem>> {
        return withContext(Dispatchers.IO) {
            val cartId = ensureCart()
            return@withContext cartItemDao.getCartItems(cartId)
        }
    }

    override suspend fun addToCart(id: Int) {
        withContext(Dispatchers.IO) {

        }
    }

    override suspend fun removeFromCart(id: Int) {

    }

    override suspend fun deleteFromCart(id: Int) {
        cartItemDao.removeCartItem(id)
    }

    override fun getProducts(): Flow<List<Product>> {
        return productDao.getProducts()
    }

    override suspend fun usernameAvailable(username: String): Boolean {
        return (userDao.usernameExists(username) == null)
    }

    override suspend fun addUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.addUser(user)
        }
    }

    override suspend fun getUserIdIfExists(username: String, password: String): Int? {
        return withContext(Dispatchers.IO) {
            return@withContext userDao.getUser(username, password)
        }
    }

    override suspend fun setActiveUser(id: Int) {
        dataStoreManager.setActiveUser(id)
    }

    override suspend fun setCart(): Int {
        return cartDao.addCart(Cart(dataStoreManager.getActiveUser(), CartStatus.PENDING)).toInt()
    }

    override suspend fun ensureCart(): Int {
        cartDao.getPendingCart(dataStoreManager.getActiveUser())?.let {
            return it
        } ?: run {
            return setCart()
        }
    }

    override suspend fun getCartItem(cartId: Int, id: Int): CartItem? {
        return cartItemDao.getCartItem(cartId, id)
    }

    override suspend fun updateCartItem(item: CartItem) {
        cartItemDao.updateCartItem(item)
    }

    override fun getProduct(idProduct: Int): Flow<Product> {
        return productDao.getProduct(idProduct)
    }

    override suspend fun addToWishlist(productId: Int) {
        withContext(Dispatchers.IO) {
            wishlistDao.addToWishlist(WishlistItem(productId, dataStoreManager.getActiveUser()))
        }
    }

    override suspend fun removeFromWishlist(productId: Int) {
        withContext(Dispatchers.IO) {
            wishlistDao.removeFromWishlist(dataStoreManager.getActiveUser(), productId)
        }
    }

    override suspend fun addCartItem(cartItem: CartItem) {
        cartItemDao.addCartItem(cartItem)
    }

    override suspend fun isWished(productId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext wishlistDao.isWished(productId,
                dataStoreManager.getActiveUser()) != null
        }
    }

    override suspend fun userIsLoggedIn(): Boolean {
        return dataStoreManager.getActiveUser() != ANONYMOUS_USER_ID
    }

    override suspend fun transferAnonymousCartToActiveUser() {
        withContext(Dispatchers.IO) {
            cartDao.cancelCart(dataStoreManager.getActiveUser())
            cartDao.transferCart(dataStoreManager.getActiveUser())
        }
    }

    override suspend fun getWishlist(): Flow<List<Product>> {
        return withContext(Dispatchers.IO) {
            wishlistDao.getWishlist(dataStoreManager.getActiveUser())
        }
    }

    override suspend fun logOut() {
        dataStoreManager.clearActiveUser()
    }
}