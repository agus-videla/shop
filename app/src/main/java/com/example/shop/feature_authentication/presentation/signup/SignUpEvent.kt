package com.example.shop.feature_authentication.presentation.signup

import android.widget.ImageView

sealed class SignUpEvent {
    data class UserChanged(val user: String?, val imgView: ImageView): SignUpEvent()
    data class PasswordChanged(val pass: String?, val imgView: ImageView): SignUpEvent()
    data class RepeatPassChanged(val pass: String?, val repeat: String?, val imgView: ImageView): SignUpEvent()
}
