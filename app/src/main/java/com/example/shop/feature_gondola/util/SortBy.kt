package com.example.shop.feature_gondola.util

sealed class SortBy(var sortOrder: SortOrder) {
    class Name(order: SortOrder): SortBy(order)
    class Price(order: SortOrder): SortBy(order)
    class Relevance(order: SortOrder): SortBy(order)
}