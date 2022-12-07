package com.example.shop.feature_authentication.domain.use_case

import com.example.shop.feature_authentication.domain.repository.UserRepository

class SetActiveUser(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.setActiveUser(id)
    }
}