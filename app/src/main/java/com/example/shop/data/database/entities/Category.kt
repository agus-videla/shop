package com.example.shop.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
class Category(
    @PrimaryKey(autoGenerate = true)val id: Int,
    @ColumnInfo val name: String
)