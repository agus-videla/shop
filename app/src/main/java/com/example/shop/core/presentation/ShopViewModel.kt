package com.example.shop.core.presentation

import androidx.lifecycle.ViewModel
import com.example.shop.core.data.repository.ShopRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val repository: ShopRepositoryImpl
): ViewModel() {
    suspend fun logOut() {
        repository.logOut()
    }
}