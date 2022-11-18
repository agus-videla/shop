package com.example.shop.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("USER_PREFERENCES")

class DataStoreManager @Inject constructor(context: Context) {
    private val userDataStore = context.dataStore
    private val activeUserKey = "ACTIVE_USER"
    companion object {
        const val ANONYMOUS_USER_ID = 1
    }

    suspend fun setActiveUser(id: Int) {
        Log.d("DATASTORE", "smt is happening...")
        userDataStore.edit { preferences ->
            preferences[intPreferencesKey(activeUserKey)] = id
        }
    }

    suspend fun clearActiveUser() {
        userDataStore.edit { preferences ->
            preferences[intPreferencesKey(activeUserKey)] = ANONYMOUS_USER_ID
        }
    }

    suspend fun getActiveUser(): Int? {
        val preferences = userDataStore.data.first().toPreferences()
        Log.d("DATASOTRE", "wtf")
        val key = preferences[intPreferencesKey(activeUserKey)]
        Log.d("DATASOTRE", "wtf x 2")
        return key
    }
}