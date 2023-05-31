package com.example.shop.core.data.data_source.callbacks

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shop.core.data.data_source.dao.UserDao
import com.example.shop.core.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider

class AddAnonUserCallback(
    private val provider: Provider<UserDao>,
) : RoomDatabase.Callback() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        Log.d("USER CALLBACK", "Creating Anonymous User")
        applicationScope.launch(Dispatchers.IO) {
            createUser()
        }
    }

    private suspend fun createUser() {
        val user = User("Anon", "", null, null)
        user.id = 1
        provider.get().addUser(user)
    }
}