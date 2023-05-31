package com.example.shop.feature_authentication.presentation.signup

import androidx.lifecycle.ViewModel
import com.example.shop.core.domain.model.User
import com.example.shop.feature_authentication.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddInfoViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    suspend fun addNewUser(user: User) {
            repository.addUser(user)
    }
}