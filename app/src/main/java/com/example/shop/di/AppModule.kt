package com.example.shop.di

import android.content.Context
import androidx.room.Room
import com.example.shop.core.data.data_source.ShopDatabase
import com.example.shop.core.data.data_source.callbacks.CategoryCallback
import com.example.shop.core.data.data_source.callbacks.ProductCallback
import com.example.shop.core.data.data_source.callbacks.UserCallback
import com.example.shop.core.data.data_source.dao.*
import com.example.shop.core.data.datastore.DataStoreManager
import com.example.shop.core.domain.use_case.IsUserLoggedIn
import com.example.shop.feature_authentication.data.repository.UserRepositoryImp
import com.example.shop.feature_authentication.domain.repository.UserRepository
import com.example.shop.feature_authentication.domain.use_case.*
import com.example.shop.feature_cart.data.repository.CartRepositoryImp
import com.example.shop.feature_cart.domain.repository.CartRepository
import com.example.shop.feature_cart.domain.use_case.*
import com.example.shop.feature_gondola.data.repository.ProductRepositoryImp
import com.example.shop.feature_gondola.data.repository.WishlistRepositoryImp
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
    ): AuthUseCases {
        return AuthUseCases(
            isUsernameAvailable = IsUsernameAvailable(userRepository),
            getUserIdIfExists = GetUserIdIfExists(userRepository),
            setActiveUser = SetActiveUser(userRepository),
            logOut = LogOut(userRepository)
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
        userDao: UserDao,
        dataStoreManager: DataStoreManager,
    ): UserRepository {
        return UserRepositoryImp(
            userDao,
            dataStoreManager
        )
    }


    @Provides
    @Singleton
    fun provideWishlistRepository(
        wishlistDao: WishlistDao,
        dataStoreManager: DataStoreManager,
    ): WishlistRepository {
        return WishlistRepositoryImp(
            wishlistDao,
            dataStoreManager
        )
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        cartDao: CartDao,
        cartItemDao: CartItemDao,
        dataStoreManager: DataStoreManager,
    ): CartRepository {
        return CartRepositoryImp(
            cartItemDao,
            cartDao,
            dataStoreManager
        )
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        productDao: ProductDao,
    ): ProductRepository {
        return ProductRepositoryImp(productDao)
    }
}
