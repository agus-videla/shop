package com.example.shop.feature_gondola.presentation.components

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop.core.data.data_source.entities.Product
import com.example.shop.databinding.FragmentGondolaBinding
import com.example.shop.feature_authentication.presentation.AuthenticationActivity
import com.example.shop.feature_gondola.presentation.GondolaEvent
import com.example.shop.feature_gondola.presentation.GondolaViewModel
import com.example.shop.feature_gondola.util.SortBy
import com.example.shop.feature_gondola.util.SortOrder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GondolaFragment : Fragment() {
    private var _binding: FragmentGondolaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GondolaViewModel by viewModels()
    private lateinit var prodAdapter: ProductAdapter
    private lateinit var wishAdapter: ProductAdapter
    private var pendingWishId: Int? = null
    private val authenticationLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                lifecycleScope.launch {
                    pendingWishId?.let {
                        viewModel.onEvent(GondolaEvent.ToggleWish(it))
                        viewModel.getWishlist()
                    }
                }
            } else {
                Toast.makeText(this.context,
                    "You must be logged to wishlist items",
                    Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGondolaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.onEach {
            Log.d("stateflow", "something's changed...")
            binding.tvWishlist.visibility = it.wishlistVisibility
            initSortOrderButton(it.productOrder, it.sortIconId)
            initProductRecyclerView(it.products)
            initWishlistRecyclerView(it.wishlist)
        }.launchIn(lifecycleScope)
        initSortBySpinner()

    }

    private fun initSortOrderButton(sortBy: SortBy, sortIcon: Int) {
        binding.btnSortOrder.apply {
            setOnClickListener {
                setImageResource(sortIcon)
                if (sortBy.sortOrder == SortOrder.Ascending)
                    sortBy.sortOrder = SortOrder.Descending
                else
                    sortBy.sortOrder = SortOrder.Ascending
                lifecycleScope.launch {
                    viewModel.onEvent(GondolaEvent.SortProducts(sortBy))
                }
            }
        }
    }

    private fun initSortBySpinner() {
        binding.spSortBy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                lifecycleScope.launch {
                    when (adapterView?.getItemAtPosition(position).toString()) {
                        "Name" ->
                            viewModel.onEvent(GondolaEvent.SortProducts(SortBy.Name(SortOrder.Descending)))
                        "Price" ->
                            viewModel.onEvent(GondolaEvent.SortProducts(SortBy.Price(SortOrder.Descending)))
                        "Relevance" ->
                            viewModel.onEvent(GondolaEvent.SortProducts(SortBy.Relevance(SortOrder.Descending)))
                    }
                }
                if (this@GondolaFragment::prodAdapter.isInitialized)
                    prodAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }
        }
    }

    private fun initProductRecyclerView(productList: List<Product>) {
        prodAdapter = ProductAdapter(
            productList,
            { id -> lifecycleScope.launch{ onAddItem(id) } },
            { id, position -> onWishlistItem(id, position) },
            { id -> viewModel.isWished(id) })
        binding.rvProducts.adapter = prodAdapter
        binding.rvProducts.layoutManager = GridLayoutManager(this.context, 2)
    }

    private fun initWishlistRecyclerView(productList: List<Product>) {
        wishAdapter = ProductAdapter(
            productList,
            { id -> lifecycleScope.launch{ onAddItem(id) } },
            { id, position -> onWishlistItem(id, position) },
            { id -> viewModel.isWished(id) })
        binding.rvWishlist.adapter = wishAdapter
        binding.rvWishlist.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    }

    private suspend fun onAddItem(id: Int) {
        viewModel.onEvent(GondolaEvent.AddToCart(id))
        Toast.makeText(this.context, "Added to Cart!", Toast.LENGTH_SHORT).show()
    }

    private fun onWishlistItem(id: Int, position: Int) {
        lifecycleScope.launch {
            if (viewModel.userIsLoggedIn()) {
                viewModel.onEvent(GondolaEvent.ToggleWish(id))
                prodAdapter.notifyItemChanged(position)
            } else {
                pendingWishId = id
                val intent = Intent(this@GondolaFragment.context, AuthenticationActivity::class.java)
                authenticationLauncher.launch(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}