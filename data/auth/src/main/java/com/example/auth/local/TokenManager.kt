package com.example.auth.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

class TokenManager(
    private val prefs: DataStore<Preferences>
) {
    private val tokenKey = stringPreferencesKey("auth_token")

    suspend fun saveToken(token: String){
        prefs.edit {
            it[tokenKey] = token
        }
    }

    fun getToken() = prefs.data.map {
        it[tokenKey]
    }
}