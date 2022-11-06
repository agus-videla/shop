package com.example.shop.ui.signup

import androidx.lifecycle.ViewModel
import com.example.shop.data.ShopRepository
import com.example.shop.data.database.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddInfoViewModel @Inject constructor(
    private val repository: ShopRepository,
) : ViewModel() {
    suspend fun addNewUser(user: User) {
            repository.addUser(user)
    }
}