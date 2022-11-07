package com.example.shop.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shop.databinding.ActivityAuthenticationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}