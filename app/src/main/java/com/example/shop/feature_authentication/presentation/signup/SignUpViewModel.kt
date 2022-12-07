package com.example.shop.feature_authentication.presentation.signup

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.example.shop.R
import com.example.shop.feature_authentication.domain.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> get() = _state

    suspend fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.UserChanged -> {
                val isUserValid =
                    (event.user?.length ?: 0) >= 4 && authUseCases.isUsernameAvailable(event.user)
                _state.update {
                    it.copy(isUserValid = isUserValid)
                }
                handleUiState(isUserValid, event.imgView)
            }
            is SignUpEvent.PasswordChanged -> {
                val isPasswordValid = (event.pass?.length ?: 0) >= 4
                _state.update {
                    it.copy(isPasswordValid = isPasswordValid)
                }
                handleUiState(isPasswordValid, event.imgView)
            }
            is SignUpEvent.RepeatPassChanged -> {
                val arePasswordsEqual = event.pass == event.repeat
                _state.update {
                    it.copy(arePasswordsEqual = arePasswordsEqual)
                }
                handleUiState(arePasswordsEqual, event.imgView)
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