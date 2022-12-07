package com.example.shop.feature_authentication.domain.use_case

import com.example.shop.feature_authentication.domain.repository.UserRepository

class GetUserIdIfExists(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(username: String, passwordHash: String): Int? {
        return repository.getUserIdIfExists(username, passwordHash)
    }
}