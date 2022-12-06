package com.example.shop.di

import android.content.Context
import androidx.room.Room
import com.example.shop.core.data.repository.ShopRepositoryImpl
import com.example.shop.core.data.data_source.ShopDatabase
import com.example.shop.core.data.data_source.callbacks.CategoryCallback
import com.example.shop.core.data.data_source.callbacks.ProductCallback
import com.example.shop.core.data.data_source.callbacks.UserCallback
import com.example.shop.core.data.data_source.dao.*
import com.example.shop.core.data.datastore.DataStoreManager
import com.example.shop.core.data.domain.use_case.IsUserLoggedIn
import com.example.shop.core.data.repository.ShopRepository
import com.example.shop.feature_authentication.domain.repository.UserRepository
import com.example.shop.feature_authentication.domain.use_case.AuthenticationUseCases
import com.example.shop.feature_authentication.domain.use_case.IsUsernameAvailable
import com.example.shop.feature_cart.domain.repository.CartRepository
import com.example.shop.feature_cart.domain.use_case.*
import com.example.shop.feature_gondola.domain.repository.ProductRepository
import com.example.shop.feature_gondola.domain.repository.WishlistRepository
import com.example.shop.feature_gondola.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShopDatabase(
        @ApplicationContext context: Context,
        categoryProvider: Provider<CategoryDao>,
        productProvider: Provider<ProductDao>,
        userProvider: Provider<UserDao>,
        cartProvider: Provider<CartDao>,
    ): ShopDatabase {
        return Room.databaseBuilder(
            context,
            ShopDatabase::class.java,
            "shop_database"
        )
            .addCallback(UserCallback(userProvider))
            .addCallback(CategoryCallback(categoryProvider))
            .addCallback(ProductCallback(productProvider))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideProductDao(db: ShopDatabase) = db.productDao()

    @Singleton
    @Provides
    fun provideCategoryDao(db: ShopDatabase) = db.categoryDao()

    @Singleton
    @Provides
    fun provideUserDao(db: ShopDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideCartDao(db: ShopDatabase) = db.cartDao()

    @Singleton
    @Provides
    fun provideCartItemDao(db: ShopDatabase) = db.cartItemDao()

    @Singleton
    @Provides
    fun provideWishlistDao(db: ShopDatabase) = db.wishlistDao()

    @Provides
    @Singleton
    fun provideCartUseCases(
        cartRepository: CartRepository,
        productRepository: ProductRepository,
        userRepository: UserRepository
    ): CartUseCases {
        return CartUseCases(
            addToCart = AddToCart(cartRepository),
            removeFromCart = RemoveFromCart(cartRepository),
            deleteFromCart = DeleteFromCart(cartRepository),
            getCartItems = GetCartItems(cartRepository,productRepository),
            transferCart = TransferCart(cartRepository),
            isUserLoggedIn = IsUserLoggedIn(userRepository)
        )
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(
        userRepository: UserRepository
    ): AuthenticationUseCases {
        return AuthenticationUseCases(
            isUsernameAvailable = IsUsernameAvailable(userRepository)
        )
    }

    @Provides
    @Singleton
    fun provideProductUseCases(
        productRepository: ProductRepository,
        wishlistRepository: WishlistRepository,
        cartRepository: CartRepository,
        userRepository: UserRepository
    ): ProductUseCases {
        return ProductUseCases(
            getProducts = GetProducts(productRepository),
            getWishlist = GetWishlist(wishlistRepository),
            toggleWished = ToggleWished(wishlistRepository),
            addToCart = AddToCart(cartRepository),
            isUserLoggedIn = IsUserLoggedIn(userRepository)
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        productDao: ProductDao,
        categoryDao: CategoryDao,
        userDao: UserDao,
        cartDao: CartDao,
        cartItemDao: CartItemDao,
        wishlistDao: WishlistDao,
        dataStoreManager: DataStoreManager,
    ): UserRepository {
        return ShopRepositoryImpl(
            productDao,
            categoryDao,
            userDao,
            cartDao,
            cartItemDao,
            wishlistDao,
            dataStoreManager)
    }


    @Provides
    @Singleton
    fun provideWishlistRepository(
        productDao: ProductDao,
        categoryDao: CategoryDao,
        userDao: UserDao,
        cartDao: CartDao,
        cartItemDao: CartItemDao,
        wishlistDao: WishlistDao,
        dataStoreManager: DataStoreManager,
    ): WishlistRepository {
        return ShopRepositoryImpl(
            productDao,
            categoryDao,
            userDao,
            cartDao,
            cartItemDao,
            wishlistDao,
            dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        productDao: ProductDao,
        categoryDao: CategoryDao,
        userDao: UserDao,
        cartDao: CartDao,
        cartItemDao: CartItemDao,
        wishlistDao: WishlistDao,
        dataStoreManager: DataStoreManager,
    ): CartRepository {
        return ShopRepositoryImpl(
            productDao,
            categoryDao,
            userDao,
            cartDao,
            cartItemDao,
            wishlistDao,
            dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        productDao: ProductDao,
        categoryDao: CategoryDao,
        userDao: UserDao,
        cartDao: CartDao,
        cartItemDao: CartItemDao,
        wishlistDao: WishlistDao,
        dataStoreManager: DataStoreManager,
    ): ProductRepository {
        return ShopRepositoryImpl(
            productDao,
            categoryDao,
            userDao,
            cartDao,
            cartItemDao,
            wishlistDao,
            dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideShopRepository(
        productDao: ProductDao,
        categoryDao: CategoryDao,
        userDao: UserDao,
        cartDao: CartDao,
        cartItemDao: CartItemDao,
        wishlistDao: WishlistDao,
        dataStoreManager: DataStoreManager,
    ): ShopRepository {
        return ShopRepositoryImpl(
            productDao,
            categoryDao,
            userDao,
            cartDao,
            cartItemDao,
            wishlistDao,
            dataStoreManager)
    }
}
