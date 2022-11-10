package com.example.shop.ui.main.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.R
import com.example.shop.data.database.entities.Product
import com.example.shop.databinding.ShopItemBinding

class ProductAdapter(
    private val products: List<Product>,
    private val onAddItem: (Int) -> Unit,
    private val onWishlistItem: (Int, Int) -> Unit,
    private val isWished: (Int) -> Boolean
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(val binding: ShopItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ShopItemBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.apply {
            ivProduct.setImageResource(R.mipmap.mountain_jacket_foreground)
            tvTitle.text = products[position].name
            tvPrice.text = products[position].price.toString()
            btnBuy.setOnClickListener {
                onAddItem(products[position].id)
            }
            btnFavourite.setOnClickListener {
                onWishlistItem(products[position].id, position)
            }
            if (isWished(products[position].id))
                btnFavourite.setImageResource(R.drawable.ic_wished)
            else
                btnFavourite.setImageResource(R.drawable.ic_unwished)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}