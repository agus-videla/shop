package com.example.shop.feature_authentication.presentation.signup

import androidx.lifecycle.ViewModel
import com.example.shop.core.data.repository.ShopRepositoryImpl
import com.example.shop.core.data.data_source.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddInfoViewModel @Inject constructor(
    private val repository: ShopRepositoryImpl,
) : ViewModel() {
    suspend fun addNewUser(user: User) {
            repository.addUser(user)
    }
}