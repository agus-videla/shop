package com.example.shop.feature_authentication.presentation.login

import androidx.lifecycle.ViewModel
import com.example.shop.core.data.repository.ShopRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    val repository: ShopRepositoryImpl,
) : ViewModel() {
    suspend fun getUserIdIfExists(username: String, password: String): Int? {
        return repository.getUserIdIfExists(username, password)
    }

    suspend fun setActiveUser(id: Int) {
        repository.setActiveUser(id)
    }

}