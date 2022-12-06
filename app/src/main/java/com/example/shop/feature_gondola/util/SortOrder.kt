package com.example.shop.feature_gondola.util

sealed class SortOrder {
    object Ascending: SortOrder()
    object Descending: SortOrder()
}
