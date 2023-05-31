package com.example.shop.feature_cart.data.repository

import com.example.shop.core.data.data_source.dao.CartDao
import com.example.shop.core.data.data_source.dao.CartItemDao
import com.example.shop.core.data.datastore.DataStoreManager
import com.example.shop.core.domain.model.Cart
import com.example.shop.core.domain.model.CartItem
import com.example.shop.core.domain.model.CartStatus
import com.example.shop.feature_cart.domain.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartRepositoryImp @Inject constructor(
    private val cartItemDao: CartItemDao,
    private val cartDao: CartDao,
    private val dataStoreManager: DataStoreManager
): CartRepository {
    override suspend fun getCartItems(): Flow<List<CartItem>> {
        return withContext(Dispatchers.IO) {
            val cartId = ensureCart()
            return@withContext cartItemDao.getCartItems(cartId)
        }
    }

    override suspend fun deleteFromCart(id: Int) {
        cartItemDao.removeCartItem(id)
    }

    override suspend fun createNewPendingCart(): Int {
        return cartDao.addCart(Cart(dataStoreManager.getActiveUser(), CartStatus.PENDING)).toInt()
    }

    override suspend fun ensureCart(): Int {
        cartDao.getPendingCart(dataStoreManager.getActiveUser())?.let {
            return it
        } ?: run {
            return createNewPendingCart()
        }
    }

    override suspend fun getCartItem(cartId: Int, id: Int): CartItem? {
        return cartItemDao.getCartItem(cartId, id)
    }

    override suspend fun updateCartItem(item: CartItem) {
        cartItemDao.updateCartItem(item)
    }

    override suspend fun addCartItem(cartItem: CartItem) {
        cartItemDao.addCartItem(cartItem)
    }

    override suspend fun transferAnonymousCartToActiveUser() {
        withContext(Dispatchers.IO) {
            cartDao.cancelCart(dataStoreManager.getActiveUser())
            cartDao.transferCart(dataStoreManager.getActiveUser())
        }
    }

}