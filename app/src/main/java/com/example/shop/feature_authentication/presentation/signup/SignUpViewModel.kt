package com.example.shop.feature_authentication.presentation.signup

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.example.shop.R
import com.example.shop.feature_authentication.domain.use_case.AuthenticationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCases: AuthenticationUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> get() = _state

    suspend fun onEvent(event: SignUpEvent) {
        val b: Boolean
        when (event) {
            is SignUpEvent.UserChanged -> {
                b = (event.user?.length ?: 0) >= 4 && authUseCases.isUsernameAvailable(event.user)
                _state.update {
                    it.copy(isUserValid = b)
                }
                handleUiState(b, event.imgView)
            }
            is SignUpEvent.PasswordChanged -> {
                b = (event.pass?.length ?: 0) >= 4
                _state.update {
                    it.copy(isPasswordValid = b)
                }
                handleUiState(b, event.imgView)
            }
            is SignUpEvent.RepeatPassChanged -> {
                b = event.pass == event.repeat
                _state.update {
                    it.copy(arePasswordsEqual = b)
                }
                handleUiState(b, event.imgView)
            }
        }
    }

    private fun handleUiState(b: Boolean?, iv: ImageView) {
        b?.let {
            iv.visibility = View.VISIBLE
        }
        when (b) {
            true -> iv.setImageResource(R.drawable.ic_check)
            false -> iv.setImageResource(R.drawable.ic_close)
            null -> iv.visibility = View.GONE
        }
    }
}