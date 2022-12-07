package com.example.shop.core.data.data_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shop.core.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT id FROM User WHERE username LIKE :username AND password_hash LIKE :hash LIMIT 1")
    fun getUser(username: String, hash: String) : Int?

    @Query("SELECT * FROM User WHERE id = :userId")
    fun getUser(userId: Int) : Flow<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User): Long

    @Query("SELECT username FROM User WHERE username LIKE :username")
    suspend fun usernameExists(username: String): String?
}