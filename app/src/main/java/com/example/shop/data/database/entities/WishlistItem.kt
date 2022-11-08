package com.example.shop.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = ["product_id","user_id"],
    tableName = "WishlistItem",
    foreignKeys =
    [ForeignKey(entity = Product::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("product_id"),
        onDelete = ForeignKey.NO_ACTION),
        ForeignKey(entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("user_id"),
            onDelete = ForeignKey.NO_ACTION)],
)
class WishlistItem(
    @ColumnInfo(name = "product_id") val idProduct: Int,
    @ColumnInfo(name = "user_id") val idUser: Int,
)

