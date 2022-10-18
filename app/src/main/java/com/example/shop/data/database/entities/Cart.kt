package com.example.shop.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Cart",
    foreignKeys = [ForeignKey(entity = User::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("user_id"),
    onDelete = ForeignKey.NO_ACTION)])
class Cart(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "user_id") val idUser: Int,
    @ColumnInfo(name = "status") val status: CartStatus
)

enum class CartStatus {
    PENDING, CANCELLED, CHECKED_OUT
}