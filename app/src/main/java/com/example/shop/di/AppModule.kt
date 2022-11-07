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
        cartItemProvider: Provider<CartItemDao>,
        userProvider: Provider<UserDao>,
        cartProvider: Provider<CartDao>
    ): ShopDatabase {
        return Room.databaseBuilder(
            context,
            ShopDatabase::class.java,
            "shop_database"
        )
            .addCallback(UserCallback(userProvider))
            .addCallback(CartItemCallback(cartItemProvider))
            .addCallback(CartCallback(cartProvider))
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

    @Provides
    @Singleton
    fun provideRepository(
        productDao: ProductDao,
        categoryDao: CategoryDao,
        userDao: UserDao,
        cartDao: CartDao,
        cartItemDao: CartItemDao,
    ): ShopRepository {
        return ShopRepository(productDao, categoryDao, userDao, cartDao, cartItemDao)
    }
}