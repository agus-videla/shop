package com.example.shop.feature_gondola.domain.use_case

import com.example.shop.feature_gondola.data.repository.FakeProductRepository
import com.example.shop.feature_gondola.util.SortBy
import com.example.shop.feature_gondola.util.SortOrder
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class GetProductsTest {

    private lateinit var repository: FakeProductRepository
    private lateinit var getProducts: GetProducts

    @Before
    fun setUp() {
        repository = FakeProductRepository()
        getProducts = GetProducts(repository)

        repository.populate()
    }

    @Test
    fun `Get products by name ascending, correct order`() = runBlocking {
        val products = getProducts(SortBy.Name(SortOrder.Ascending)).first()
        for(i in 0..products.size-2) {
            assertThat(products[i].name).isLessThan(products[i+1].name)
        }
    }

    @Test
    fun `Get products by name descending, correct order`() = runBlocking {
        val products = getProducts(SortBy.Name(SortOrder.Descending)).first()
        for(i in 0..products.size-2) {
            assertThat(products[i].name).isGreaterThan(products[i+1].name)
        }
    }

    @Test
    fun `Get products by price ascending, correct order`() = runBlocking {
        val products = getProducts(SortBy.Price(SortOrder.Ascending)).first()
        for(i in 0..products.size-2) {
            assertThat(products[i].price).isLessThan(products[i+1].price)
        }
    }

    @Test
    fun `Get products by price descending, correct order`() = runBlocking {
        val products = getProducts(SortBy.Price(SortOrder.Descending)).first()
        for(i in 0..products.size-2) {
            assertThat(products[i].price).isGreaterThan(products[i+1].price)
        }
    }

}
