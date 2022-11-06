package com.example.shop.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shop.databinding.ActivityMainBinding
import com.example.shop.ui.login.LoginActivity
import com.example.shop.ui.shop.ShopActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            openActivity(intent)
        }
        binding.btnShop.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            openActivity(intent)
        }
    }

    private fun openActivity(intent: Intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }
}