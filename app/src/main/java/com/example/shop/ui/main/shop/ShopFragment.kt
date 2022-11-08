package com.example.shop.ui.main.shop

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop.R
import com.example.shop.databinding.FragmentShopBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShopFragment : Fragment() {
    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShopViewModel by viewModels()
    private lateinit var adapter: ProductAdapter

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

        initProductRecyclerView()
        initWishlistRecyclerView()
        initSortBySpinner()
        initSortOrderButton()
    }

    private fun initSortOrderButton() {
        viewModel.sortOrder.observe(viewLifecycleOwner) { sortOrder ->
            when (sortOrder!!) {
                SortOrder.ASC -> binding.btnSortOrder.setImageResource(R.drawable.ic_asc)
                SortOrder.DESC -> binding.btnSortOrder.setImageResource(R.drawable.ic_desc)
            }
        }
        binding.btnSortOrder.setOnClickListener {
            viewModel.changeSortOrder()
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
                if (this@ShopFragment::adapter.isInitialized)
                    adapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }
        }
    }

    private fun initProductRecyclerView() {
        viewModel.products.observe(viewLifecycleOwner) { productList ->
            adapter = ProductAdapter(
                productList,
                { id -> onAddItem(id) },
                { id -> onWishlistItem(id) }
            )

            binding.rvProducts.adapter = adapter
            binding.rvProducts.layoutManager = GridLayoutManager(this.context, 2)
        }
    }

    private fun initWishlistRecyclerView() {
        viewModel.wishlist.observe(viewLifecycleOwner) {
            adapter = ProductAdapter(
                it,
                { id -> onAddItem(id) },
                { id -> onWishlistItem(id) })

            binding.rvWishlist.adapter = adapter
            binding.rvWishlist.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun onAddItem(id: Int) {
        viewModel.addToCart(id)
        Toast.makeText(this.context, "Added to Cart!", Toast.LENGTH_SHORT).show()
    }

    private fun onWishlistItem(id: Int) {
        viewModel.wishlistItem(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}