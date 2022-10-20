package com.example.shop.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shop.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    companion object {
        const val CAMERA_REQUEST_CODE = 1
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}