package com.example.shop.core.presentation

import androidx.lifecycle.ViewModel
import com.example.shop.feature_authentication.domain.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
): ViewModel() {
    suspend fun logOut() {
        authUseCases.logOut()
    }
}