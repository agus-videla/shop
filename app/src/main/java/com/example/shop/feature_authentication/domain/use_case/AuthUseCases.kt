package com.example.shop.feature_authentication.domain.use_case

data class AuthUseCases(
    val isUsernameAvailable: IsUsernameAvailable,
    val getUserIdIfExists: GetUserIdIfExists,
    val setActiveUser: SetActiveUser
)
