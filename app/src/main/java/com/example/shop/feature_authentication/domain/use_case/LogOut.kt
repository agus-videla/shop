package com.example.shop.feature_authentication.domain.use_case

import com.example.shop.feature_authentication.domain.repository.UserRepository

class LogOut(
    private val repository: UserRepository
) {
    suspend operator fun invoke() {
        repository.logOut()
    }
}