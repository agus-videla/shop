package com.example.shop.di

import android.content.Context
import androidx.room.Room
import com.example.shop.data.ShopRepository
import com.example.shop.data.database.ShopDatabase
import com.example.shop.data.database.callbacks.*
import com.example.shop.data.database.dao.*
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
        cartProvider: Provider<CartDao>
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
    fun provideRepository(
        productDao: ProductDao,
        categoryDao: CategoryDao,
        userDao: UserDao,
        cartDao: CartDao,
        cartItemDao: CartItemDao,
        wishlistDao: WishlistDao
    ): ShopRepository {
        return ShopRepository(productDao, categoryDao, userDao, cartDao, cartItemDao, wishlistDao)
    }
}