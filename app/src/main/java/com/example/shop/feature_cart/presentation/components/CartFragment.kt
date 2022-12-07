package com.example.shop.feature_cart.presentation.components

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop.databinding.FragmentCartBinding
import com.example.shop.feature_authentication.presentation.AuthenticationActivity
import com.example.shop.feature_cart.presentation.CartEvent
import com.example.shop.feature_cart.presentation.CartViewModel
import com.example.shop.feature_cart.util.CartItemWithProduct
import com.example.shop.ui.ShippingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModels()
    private lateinit var adapter: CartItemAdapter
    private val authenticationLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                lifecycleScope.launch {
                    viewModel.onEvent(CartEvent.TransferCart)
                }
                openShippingActivity()
            } else {
                Toast.makeText(this.context,
                    "You must be logged to start the checkout process",
                    Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.onEach { state ->
            binding.tvFinalPrice.text = state.cartItems.sumOf { it.subTotal() }.toString()
            initRecyclerView(state.cartItems)
        }.launchIn(lifecycleScope)

        binding.btnCheckout.setOnClickListener {
            lifecycleScope.launch {
                viewModel.onEvent(CartEvent.IsUserLoggedIn)
                if (viewModel.state.value.userIsLoggedIn == true) {
                    openShippingActivity()
                } else {
                    val intent =
                        Intent(this@CartFragment.context, AuthenticationActivity::class.java)
                    authenticationLauncher.launch(intent)
                }
            }
        }
    }

    private fun initRecyclerView(cartItems: List<CartItemWithProduct>) {
        adapter = CartItemAdapter(
            cartItems,
            { id, position -> lifecycleScope.launch { onAddItem(id, position) } },
            { id, position -> lifecycleScope.launch { onRemoveItem(id, position) } },
            { id, position -> lifecycleScope.launch { onDeleteItem(id, position) } }
        )
        binding.rvProducts.adapter = adapter
        binding.rvProducts.layoutManager = LinearLayoutManager(this.context)
    }

    private suspend fun onAddItem(id: Int, position: Int) {
        viewModel.onEvent(CartEvent.AddToCart(id))
        adapter.notifyItemChanged(position)
    }

    private suspend fun onRemoveItem(id: Int, position: Int) {
        viewModel.onEvent(CartEvent.RemoveFromCart(id))
        adapter.notifyItemChanged(position)
    }

    private suspend fun onDeleteItem(id: Int, position: Int) {
        viewModel.onEvent(CartEvent.DeleteFromCart(id))
        adapter.notifyItemRemoved(position)
    }

    private fun openShippingActivity() {
        val intent = Intent(this.context, ShippingActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}