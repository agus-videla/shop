package com.example.shop.feature_gondola.presentation

import android.view.View
import com.example.shop.R
import com.example.shop.core.domain.model.Product
import com.example.shop.feature_gondola.util.SortBy
import com.example.shop.feature_gondola.util.SortOrder

data class GondolaState(
    var userIsLoggedIn: Boolean? = null,
    var products: List<Product> = emptyList(),
    var wishlist: List<Product> = emptyList(),
    var wishlistVisibility: Int = View.GONE,
    var productOrder: SortBy = SortBy.Name(SortOrder.Descending),
    var sortIconId: Int = R.drawable.ic_desc
)
