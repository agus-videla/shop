package com.example.shop.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.shop.R
import com.example.shop.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miCart -> {
                openCartActivity()
                Toast.makeText(this, "clicked on cart!", Toast.LENGTH_SHORT).show()
            }
            R.id.miWishlist -> {
                openWishlistFragment()
                Toast.makeText(this, "clicked on wishlist!", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    private fun openWishlistFragment() {
    }

    private fun openCartActivity() {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigateUp()
        navController.navigate(R.id.cartFragment)
    }
}