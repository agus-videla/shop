package com.example.shop.feature_authentication.domain.repository

import com.example.shop.core.data.data_source.entities.User

interface UserRepository {
    suspend fun usernameAvailable(username: String): Boolean
    suspend fun addUser(user: User)
    suspend fun getUserIdIfExists(username: String, password: String): Int?
    suspend fun setActiveUser(id: Int)
    suspend fun userIsLoggedIn(): Boolean
    suspend fun logOut()
}