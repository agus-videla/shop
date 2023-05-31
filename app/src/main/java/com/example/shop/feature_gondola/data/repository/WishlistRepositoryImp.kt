package com.example.shop.feature_gondola.data.repository

import com.example.shop.core.data.data_source.dao.WishlistDao
import com.example.shop.core.data.datastore.DataStoreManager
import com.example.shop.core.domain.model.Product
import com.example.shop.core.domain.model.WishlistItem
import com.example.shop.feature_gondola.domain.repository.WishlistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WishlistRepositoryImp @Inject constructor(
    private val wishlistDao: WishlistDao,
    private val dataStoreManager: DataStoreManager
): WishlistRepository {
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

    override suspend fun isWished(productId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext wishlistDao.isWished(productId,
                dataStoreManager.getActiveUser()) != null
        }
    }

    override suspend fun getWishlist(): Flow<List<Product>> {
        return withContext(Dispatchers.IO) {
            wishlistDao.getWishlist(dataStoreManager.getActiveUser())
        }
    }
}