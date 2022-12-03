package com.example.shop.feature_shop.presentation.shop

import android.view.View
import com.example.shop.R
import com.example.shop.core.data.data_source.entities.Product
import com.example.shop.feature_shop.util.SortBy
import com.example.shop.feature_shop.util.SortOrder

data class ShopState(
    var products: List<Product> = emptyList(),
    var wishlist: List<Product> = emptyList(),
    var wishlistVisibility: Int = View.GONE,
    var productOrder: SortBy = SortBy.Name(SortOrder.Descending),
    var sortIconId: Int = R.drawable.ic_desc
)
