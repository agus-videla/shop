package com.example.shop.core.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.shop.R
import com.example.shop.databinding.ActivityShopBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class ShopActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopBinding
    private val viewModel: ShopViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miCart -> {
                openCartActivity()
            }
            R.id.miLogOut -> {
                logOut()
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    private fun logOut() {
        runBlocking {
            viewModel.logOut()
        }
        val intent = Intent(applicationContext, StartActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun openCartActivity() {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigateUp()
        navController.navigate(R.id.cartFragment)
    }
}