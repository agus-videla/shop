package com.example.shop.core.domain.use_case

import com.example.shop.feature_authentication.domain.repository.UserRepository

class IsUserLoggedIn(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.userIsLoggedIn()
    }
}