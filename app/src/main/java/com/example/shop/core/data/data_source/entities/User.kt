package com.example.shop.core.data.data_source.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
class User(
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password_hash") val pass: String,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "profile_picture") val pfp: Bitmap?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}