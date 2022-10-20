package com.example.shop.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shop.data.database.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE username == :username AND password_hash == :hash LIMIT 1")
    fun getUser(username: String, hash: String) : Flow<User>

    @Query("SELECT * FROM User WHERE id = :userId")
    fun getUser(userId: Int) : Flow<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User): Long

    @Query("SELECT username FROM User WHERE username LIKE :username")
    suspend fun usernameExists(username: String): String?
}