package com.example.shop.feature_gondola.data.repository

import com.example.shop.core.domain.model.Product
import com.example.shop.feature_gondola.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeProductRepository : ProductRepository {
    private var products = emptyList<Product>()

    fun populate() {
        val list = mutableListOf<Product>()
        val names = ('a'..'z').shuffled()
        val prices = (1L..32L).shuffled()
        val nameWithPrice = names.zip(prices)
        nameWithPrice.forEach { tuple ->
            list.add(Product(
                id = 0,
                name = tuple.first.toString(),
                price = tuple.second,
                stock = 0,
                description = "stuff",
                thumbnail = "thumb",
                idCategory = 1
            ))
        }
        products = list
    }

    override fun getProducts(): Flow<List<Product>> {
        return flow {
            emit(products)
        }
    }

    override suspend fun getProduct(idProduct: Int): Product {
            return products[idProduct]
    }

}