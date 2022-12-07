package com.example.shop.feature_authentication.data.repository

import com.example.shop.core.data.data_source.dao.UserDao
import com.example.shop.core.domain.model.User
import com.example.shop.core.data.datastore.DataStoreManager
import com.example.shop.feature_authentication.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val userDao: UserDao,
    private val dataStoreManager: DataStoreManager
): UserRepository {

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

    override suspend fun userIsLoggedIn(): Boolean {
        return dataStoreManager.getActiveUser() != DataStoreManager.ANONYMOUS_USER_ID
    }

    override suspend fun logOut() {
        dataStoreManager.clearActiveUser()
    }
}