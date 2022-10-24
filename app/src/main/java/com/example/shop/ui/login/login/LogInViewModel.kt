package com.example.shop.ui.login.login

import androidx.lifecycle.ViewModel
import com.example.shop.data.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    val repository: ShopRepository,
) : ViewModel() {
    suspend fun isValid(username: String, password: String): Boolean {
        return repository.isValid(username, password)
    }

}