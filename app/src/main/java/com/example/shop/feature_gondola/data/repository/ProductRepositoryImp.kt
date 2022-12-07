package com.example.shop.feature_gondola.data.repository

import com.example.shop.core.data.data_source.dao.ProductDao
import com.example.shop.core.domain.model.Product
import com.example.shop.feature_gondola.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImp @Inject constructor(
    private val productDao: ProductDao
): ProductRepository{
    override fun getProducts(): Flow<List<Product>> {
        return productDao.getProducts()
    }

    override fun getProduct(idProduct: Int): Flow<Product> {
        return productDao.getProduct(idProduct)
    }
}