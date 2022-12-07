package com.example.shop.core.domain.model

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
    @ColumnInfo(name = "user_id") val idUser: Int,
    @ColumnInfo(name = "status") val status: CartStatus
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

enum class CartStatus {
    PENDING, CANCELLED, CHECKED_OUT
}