package com.example.shop.di

import android.content.Context
import androidx.room.Room
import com.example.shop.data.ShopRepository
import com.example.shop.data.database.ShopDatabase
import com.example.shop.data.database.callbacks.CategoryCallback
import com.example.shop.data.database.callbacks.ProductCallback
import com.example.shop.data.database.dao.CategoryDao
import com.example.shop.data.database.dao.ProductDao
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
        productProvider: Provider<ProductDao>
    ): ShopDatabase {
        return Room.databaseBuilder(
            context,
            ShopDatabase::class.java,
            "shop_database"
        )
            .addCallback(CategoryCallback(categoryProvider))
            .fallbackToDestructiveMigration()
            .addCallback(ProductCallback(productProvider))
            .build()
    }

    @Singleton
    @Provides
    fun provideProductDao(db: ShopDatabase) = db.productDao()

    @Singleton
    @Provides
    fun provideCategoryDao(db: ShopDatabase) = db.categoryDao()

    @Provides
    @Singleton
    fun provideRepository(productDao: ProductDao, categoryDao: CategoryDao): ShopRepository {
        return ShopRepository(productDao, categoryDao)
    }
}