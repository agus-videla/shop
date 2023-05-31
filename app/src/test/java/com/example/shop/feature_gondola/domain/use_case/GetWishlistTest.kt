package com.example.shop.feature_gondola.domain.use_case

import com.example.shop.feature_gondola.data.repository.FakeWishlistRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class GetWishlistTest {

    private lateinit var repository: FakeWishlistRepository
    private lateinit var getWishlist: GetWishlist

    @Before
    fun setUp() {
        repository = FakeWishlistRepository()
        getWishlist = GetWishlist(repository)
    }

    @Test
    fun `Add 3 items to wishlist, get same 3`() = runBlocking {
        for (id in 1..3) {
            repository.addToWishlist(id)
        }

        val wishlist = getWishlist().first()

        for (id in 1..3) {
            assertThat(wishlist.filter { it.id == id }).isNotEmpty()
        }
    }

}