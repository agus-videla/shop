package com.example.shop.ui.authentication.signup

import androidx.lifecycle.ViewModel
import com.example.shop.data.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: ShopRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<uiState>(uiState())
    val uiState: StateFlow<uiState> get() = _uiState.asStateFlow()

    suspend fun validateUsername(username: CharSequence?) : Boolean {
        return repository.usernameAvailable(username.toString())
    }

    fun isUserValid(b: Boolean) {
        _uiState.update {
            it.copy(isUserValid = b)
        }
    }

    fun isPasswordValid(b: Boolean) {
        _uiState.update {
            it.copy(isPasswordValid = b)
        }
    }

    fun arePasswordsEqual(b: Boolean) {
        _uiState.update {
            it.copy(arePasswordsEqual = b)
        }
    }
}

data class uiState(
    var isUserValid: Boolean? = null,
    var arePasswordsEqual: Boolean? = null,
    var isPasswordValid: Boolean? = null
)