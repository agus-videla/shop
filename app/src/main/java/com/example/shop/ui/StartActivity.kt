package com.example.shop.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shop.databinding.ActivityStartBinding
import com.example.shop.ui.authentication.AuthenticationActivity
import com.example.shop.ui.main.MainActivity

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogIn.setOnClickListener {
            val intent = Intent(this, AuthenticationActivity::class.java)
            openActivity(intent)
        }
        binding.btnShop.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            openActivity(intent)
        }
    }

    private fun openActivity(intent: Intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }
}