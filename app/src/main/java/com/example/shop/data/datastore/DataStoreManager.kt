package com.example.shop.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("USER_PREFERENCES")

class DataStoreManager @Inject constructor(@ApplicationContext context: Context) {
    private val userDataStore = context.dataStore
    private val activeUserKey = "ACTIVE_USER"

    companion object {
        const val ANONYMOUS_USER_ID = 1
    }

    suspend fun setActiveUser(id: Int) {
        withContext(Dispatchers.IO) {
            userDataStore.edit { preferences ->
                preferences[intPreferencesKey(activeUserKey)] = id
            }
        }
    }

    suspend fun getActiveUser(): Int {
        return withContext(Dispatchers.IO) {
            val preferences = userDataStore.data.first()
            return@withContext preferences[intPreferencesKey(activeUserKey)] ?: ANONYMOUS_USER_ID
        }
    }

    suspend fun clearActiveUser() {
        withContext(Dispatchers.IO) {
            userDataStore.edit { preferences ->
                preferences[intPreferencesKey(activeUserKey)] = ANONYMOUS_USER_ID
            }
        }
    }
}