package com.example.shop.core.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
class Category(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String
) {
}