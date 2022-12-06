package com.example.shop.core.data.repository

import com.example.shop.feature_authentication.domain.repository.UserRepository
import com.example.shop.feature_cart.domain.repository.CartRepository
import com.example.shop.feature_gondola.domain.repository.ProductRepository
import com.example.shop.feature_gondola.domain.repository.WishlistRepository

interface ShopRepository: ProductRepository, CartRepository, WishlistRepository, UserRepository