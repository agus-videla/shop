package com.example.shop.feature_gondola.domain.use_case

import com.example.shop.feature_gondola.data.repository.FakeWishlistRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class ToggleWishedTest {

    private lateinit var repository: FakeWishlistRepository
    private lateinit var toggleWished: ToggleWished

    @Before
    fun setUp() {
        repository = FakeWishlistRepository()
        toggleWished = ToggleWished(repository)
    }

    @Test
    fun `Toggle product 1 time, get wishlist of size 1`() = runBlocking {
        toggleWished(1)
        assertThat(repository.getWishlist().first()).hasSize(1)
    }

    @Test
    fun `Toggle product 2 times, get wishlist of size 0`() = runBlocking {
        toggleWished(1)
        toggleWished(1)
        assertThat(repository.getWishlist().first()).isEmpty()
    }

    @Test
    fun `Toggle 3 products 1 time, get wishlist of size 3`() = runBlocking {
        toggleWished(1)
        toggleWished(2)
        toggleWished(3)
        assertThat(repository.getWishlist().first()).hasSize(3)
    }
}