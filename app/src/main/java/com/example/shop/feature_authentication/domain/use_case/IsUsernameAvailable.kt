package com.example.shop.feature_authentication.domain.use_case

import com.example.shop.feature_authentication.domain.repository.UserRepository

class IsUsernameAvailable(
    private val repository: UserRepository
) {
    suspend operator fun invoke(username: String?): Boolean {
        return username?.let {
            repository.usernameAvailable(username)
        } ?: false
    }
}