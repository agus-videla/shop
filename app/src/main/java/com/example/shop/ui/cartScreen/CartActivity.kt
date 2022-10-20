package com.example.shop.ui.cartScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop.databinding.ActivityCartBinding
import com.example.shop.ui.ShippingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartItemAdapter
    private val viewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            initRecyclerView()
        }
        binding.btnCheckout.setOnClickListener {
            openShippingActivity()
        }
    }

    private suspend fun initRecyclerView() {
        viewModel.items.collect { cartItems ->
            adapter = CartItemAdapter(
                cartItems,
                { id, position -> onAddItem(id, position) },
                { id, position -> onRemoveItem(id, position) },
                { id, position -> onDeleteItem(id, position) }
            )
            binding.rvProducts.adapter = adapter
            binding.rvProducts.layoutManager = LinearLayoutManager(this)
            binding.tvFinalPrice.text = cartItems.sumOf { it.subTotal() }.toString()
        }
    }

    private fun onAddItem(id: Int, position: Int) {
        viewModel.addToCart(id)
        adapter.notifyItemChanged(position)
    }

    private fun onRemoveItem(id: Int, position: Int) {
        viewModel.removeFromCart(id)
        adapter.notifyItemChanged(position)
    }

    private fun onDeleteItem(id: Int, position: Int) {
        viewModel.deleteFromCart(id)
        adapter.notifyItemRemoved(position)
    }

    private fun openShippingActivity() {
        val intent = Intent(this, ShippingActivity::class.java)
        startActivity(intent)
    }

}