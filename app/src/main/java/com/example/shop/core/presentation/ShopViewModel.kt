package com.example.shop.core.presentation

import androidx.lifecycle.ViewModel
import com.example.shop.feature_authentication.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {
    suspend fun logOut() {
        repository.logOut()
    }
}