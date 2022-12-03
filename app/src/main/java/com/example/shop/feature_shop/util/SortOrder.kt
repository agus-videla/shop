package com.example.shop.feature_shop.util

sealed class SortOrder {
    object Ascending: SortOrder()
    object Descending: SortOrder()
}
