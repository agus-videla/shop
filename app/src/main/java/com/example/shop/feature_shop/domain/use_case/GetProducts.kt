package com.example.shop.feature_shop.domain.use_case

import com.example.shop.core.data.data_source.entities.Product
import com.example.shop.feature_shop.domain.repository.ProductRepository
import com.example.shop.feature_shop.util.SortBy
import com.example.shop.feature_shop.util.SortOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetProducts(
    private val repository: ProductRepository
) {
    operator fun invoke(
        sortBy: SortBy = SortBy.Name(SortOrder.Descending),
    ): Flow<List<Product>> {
        return repository.getProducts().map { products ->
            when (sortBy.sortOrder) {
                is SortOrder.Ascending -> {
                    when(sortBy) {
                        is SortBy.Name -> products.sortedBy { it.name.lowercase() }
                        is SortBy.Price -> products.sortedBy { it.price }
                        is SortBy.Relevance -> products.shuffled()
                    }
                }
                is SortOrder.Descending -> {
                    when(sortBy) {
                        is SortBy.Name -> products.sortedByDescending { it.name.lowercase() }
                        is SortBy.Price -> products.sortedByDescending { it.price }
                        is SortBy.Relevance -> products.shuffled()
                    }
                }
            }
        }
    }
}