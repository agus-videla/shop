package com.example.shop.ui.main.shop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import com.example.shop.R
import com.example.shop.data.database.entities.Product
import com.example.shop.databinding.FragmentShopBinding
import com.example.shop.ui.authentication.AuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ShopFragment : Fragment() {
    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShopViewModel by viewModels()
    private lateinit var prodAdapter: ProductAdapter
    private lateinit var wishAdapter: ProductAdapter
    private var pendingWishId: Int? = null
    private val authenticationLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                lifecycleScope.launch {
                    pendingWishId?.let {
                        toggleWish(it)
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
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.products.observe(viewLifecycleOwner) { productList ->
            initProductRecyclerView(productList)
        }
        viewModel.wishlist.observe(viewLifecycleOwner) { productList ->
            if (productList.isNotEmpty())
                binding.tvWishlist.visibility = View.VISIBLE
            else
                binding.tvWishlist.visibility = View.GONE
            initWishlistRecyclerView(productList)
        }
        viewModel.sortOrder.observe(viewLifecycleOwner) { sortOrder ->
            initSortOrderButton(sortOrder)
        }
        initSortBySpinner()

    }

    private fun initSortOrderButton(sortOrder: SortOrder) {
        when (sortOrder) {
            SortOrder.ASC -> binding.btnSortOrder.setImageResource(R.drawable.ic_asc)
            SortOrder.DESC -> binding.btnSortOrder.setImageResource(R.drawable.ic_desc)
        }
        binding.btnSortOrder.setOnClickListener {
            lifecycleScope.launch {
                viewModel.testDatastore()
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
                val selection = adapterView?.getItemAtPosition(position).toString()
                viewModel.sortBy(selection)
                if (this@ShopFragment::prodAdapter.isInitialized)
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
            { id -> onAddItem(id) },
            { id, position -> onWishlistItem(id, position) },
            { id -> viewModel.isWished(id)} )
        binding.rvProducts.adapter = prodAdapter
        binding.rvProducts.layoutManager = GridLayoutManager(this.context, 2)
    }

    private fun initWishlistRecyclerView(productList: List<Product>) {
        wishAdapter = ProductAdapter(
            productList,
            { id -> onAddItem(id) },
            { id, position -> onWishlistItem(id,position) },
            { id -> viewModel.isWished(id) })

        binding.rvWishlist.adapter = wishAdapter
        binding.rvWishlist.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun onAddItem(id: Int) {
        viewModel.addToCart(id)
        Toast.makeText(this.context, "Added to Cart!", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onWishlistItem(id: Int, position: Int) {
        if (viewModel.userIsLoggedIn()) {
            toggleWish(id)
            prodAdapter.notifyItemChanged(position) // fixme icon does not update
        } else {
            pendingWishId = id
            val intent = Intent(this.context, AuthenticationActivity::class.java)
            authenticationLauncher.launch(intent)
        }
    }

    private fun toggleWish(id: Int) {
        if (viewModel.isWished(id))
            viewModel.removeFromWishlist(id)
        else
            viewModel.addToWishlist(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}