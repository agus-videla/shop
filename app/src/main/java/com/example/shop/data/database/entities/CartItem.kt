package com.example.shop.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Cart",
    foreignKeys =
    [ForeignKey(entity = Product::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("product_id"),
        onDelete = ForeignKey.NO_ACTION),
    ForeignKey(entity = Cart::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("cart_id"),
        onDelete = ForeignKey.NO_ACTION)],
)
class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "product_id") val idProduct: Int,
    @ColumnInfo(name = "cart_id") val idCart: Int,
    @ColumnInfo(name = "quantity") val quantity: Int,
)