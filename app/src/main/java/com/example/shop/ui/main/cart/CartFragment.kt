package com.example.shop.ui.main.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop.databinding.FragmentCartBinding
import com.example.shop.ui.ShippingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CartItemAdapter
    private val viewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            initRecyclerView()
        }

        viewModel.items.observe(viewLifecycleOwner) { cartItems ->
            binding.tvFinalPrice.text = cartItems.sumOf { it.subTotal() }.toString()
        }

        binding.btnCheckout.setOnClickListener {
            openShippingActivity()
        }
    }

    private fun initRecyclerView() {
        viewModel.items.observe(viewLifecycleOwner) { cartItems ->
            adapter = CartItemAdapter(
                cartItems,
                { id, position -> onAddItem(id, position) },
                { id, position -> onRemoveItem(id, position) },
                { id, position -> onDeleteItem(id, position) }
            )
            binding.rvProducts.adapter = adapter
            binding.rvProducts.layoutManager = LinearLayoutManager(this.context)
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
        val intent = Intent(this.context, ShippingActivity::class.java)
        startActivity(intent)
    }

}