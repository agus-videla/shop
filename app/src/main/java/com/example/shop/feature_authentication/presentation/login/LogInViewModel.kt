package com.example.shop.feature_authentication.presentation.login

import androidx.lifecycle.ViewModel
import com.example.shop.feature_authentication.domain.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {


    suspend fun getUserIdIfExists(username: String, password: String): Int? {
        return authUseCases.getUserIdIfExists(username,password)
    }

    suspend fun setActiveUser(id: Int) {
        authUseCases.setActiveUser(id)
    }
}