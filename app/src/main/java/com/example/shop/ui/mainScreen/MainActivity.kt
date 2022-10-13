package com.example.shop.ui.mainScreen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shop.R
import com.example.shop.databinding.ActivityMainBinding
import com.example.shop.ui.cartScreen.CartActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.products.observe(this) {
            adapter = ProductAdapter(it) { id -> onAddItem(id) }
            binding.rvProducts.adapter = adapter
            binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
        }

    }

    private fun onAddItem(id: Int) {
        viewModel.addToCart(id)
        Toast.makeText(this, "Added to Cart!", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miCart ->
            {
                openCartActivity()
                Toast.makeText(this, "clicked on cart!", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    private fun openCartActivity() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }
}