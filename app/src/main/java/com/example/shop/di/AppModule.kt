package com.example.shop.di

import android.content.Context
import androidx.room.Room
import com.example.shop.core.data.data_source.ShopDatabase
import com.example.shop.core.data.data_source.callbacks.AddCategoriesCallback
import com.example.shop.core.data.data_source.callbacks.AddProductsCallback
import com.example.shop.core.data.data_source.callbacks.AddAnonUserCallback
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
    fun provideProductDao(db: ShopDatabase) = db.productDao()

    @Singleton
    @Provides
    fun provideCategoryDao(db: ShopDatabase) = db.categoryDao()

    @Singleton
    @Provides
    fun provideUserDao(db: ShopDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideShopDatabase(
        @ApplicationContext context: Context,
        categoryProvider: Provider<CategoryDao>,
        productProvider: Provider<ProductDao>,
        userProvider: Provider<UserDao>,
    ): ShopDatabase {
        return Room.databaseBuilder(
            context,
            ShopDatabase::class.java,
            "shop_database"
        )
            .addCallback(AddAnonUserCallback(userProvider))
            .addCallback(AddCategoriesCallback(categoryProvider))
            .addCallback(AddProductsCallback(productProvider))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        db: ShopDatabase,
        dataStoreManager: DataStoreManager,
    ): UserRepository {
        return UserRepositoryImp(
            db.userDao(),
            dataStoreManager
        )
    }


    @Provides
    @Singleton
    fun provideWishlistRepository(
        db: ShopDatabase,
        dataStoreManager: DataStoreManager,
    ): WishlistRepository {
        return WishlistRepositoryImp(
            db.wishlistDao(),
            dataStoreManager
        )
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        db: ShopDatabase,
        dataStoreManager: DataStoreManager,
    ): CartRepository {
        return CartRepositoryImp(
            db.cartItemDao(),
            db.cartDao(),
            dataStoreManager
        )
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        db: ShopDatabase
    ): ProductRepository {
        return ProductRepositoryImp(db.productDao())
    }

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
            getCartItems = GetCartItems(cartRepository, productRepository),
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
}