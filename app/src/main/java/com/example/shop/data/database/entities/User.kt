package com.example.shop.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password_hash") val pass: String,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "profile_picture") val pfp: String
)