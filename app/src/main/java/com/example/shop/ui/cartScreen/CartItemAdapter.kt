package com.example.shop.ui.cartScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.R
import com.example.shop.data.CartItem
import com.example.shop.databinding.CartItemBinding

class CartItemAdapter(
    private val items: List<CartItem>,
    private val onAddItem: (Int, Int) -> Unit,
    private val onRemoveItem: (Int, Int) -> Unit,
    private val onDeleteItem: (Int, Int) -> Unit,
) : RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {

    inner class CartItemViewHolder(val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CartItemBinding.inflate(layoutInflater, parent, false)
        return CartItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.binding.apply {
            ivProduct.setImageResource(R.mipmap.mountain_jacket)
            tvId.text = items[position].product.id.toString()
            tvTitle.text = items[position].product.name
            tvQuantity.text = items[position].quantity.toString()
            tvPrice.text = items[position].product.price.toString()
            btnAdd.setOnClickListener {
                onAddItem(items[position].product.id,
                    holder.absoluteAdapterPosition)
            }
            btnMinus.setOnClickListener {
                onRemoveItem(items[position].product.id,
                    holder.absoluteAdapterPosition)
            }
            btnRemove.setOnClickListener {
                onDeleteItem(items[position].product.id,
                    holder.absoluteAdapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}