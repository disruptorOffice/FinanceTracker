package com.disruptoroffice.financetracker.config
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "session_prefs")

class SessionPreferences(private val context: Context) {

    companion object {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val SESSION_TOKEN = stringPreferencesKey("session_token")
        val USER_ID = stringPreferencesKey("session_user_id")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val FIRST_NAME = stringPreferencesKey("first_name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val USERNAME = stringPreferencesKey("username")
    }

    val isLoggedIng: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[IS_LOGGED_IN] ?: false }

    val sessionToken: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[SESSION_TOKEN] ?: "" }

    val userId: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[USER_ID] ?: ""}

    val refreshToken: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[REFRESH_TOKEN] ?: ""}

    val firstName: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[FIRST_NAME] ?: "" }

    val lastName: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[LAST_NAME] ?: "" }

    val username: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[USERNAME] ?: "" }

    suspend fun storeSessionToken(
        token: String,
        userId: String,
        firstName: String,
        lastName: String,
        username: String,
    ) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
            preferences[SESSION_TOKEN] = token
            preferences[USER_ID] = userId
            preferences[FIRST_NAME] = firstName
            preferences[LAST_NAME] = lastName
            preferences[USERNAME] = username
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs[IS_LOGGED_IN] = false
            prefs[SESSION_TOKEN] = ""
            prefs[USER_ID] = ""
            prefs[REFRESH_TOKEN] = ""
            prefs[FIRST_NAME] = ""
            prefs[LAST_NAME] = ""
            prefs[USERNAME] = ""
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[SESSION_TOKEN] = token
        }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        context.dataStore.edit { prefs ->
            prefs[REFRESH_TOKEN] = refreshToken
        }
    }

}
