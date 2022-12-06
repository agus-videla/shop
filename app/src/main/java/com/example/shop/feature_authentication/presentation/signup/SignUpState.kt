package com.example.shop.feature_authentication.presentation.signup

data class SignUpState(
    var isUserValid: Boolean? = null,
    var arePasswordsEqual: Boolean? = null,
    var isPasswordValid: Boolean? = null
)
