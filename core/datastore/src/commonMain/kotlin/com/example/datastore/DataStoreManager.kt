package com.example.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

class DataStoreManager(
    private val prefs: DataStore<Preferences>
) {
    private val tokenKey = stringPreferencesKey("auth_token")

    suspend fun saveToken(token: String){
        prefs.edit {
            it[tokenKey] = token
        }
    }

    suspend fun getToken() = prefs.data.map {
        it[tokenKey]
    }
}