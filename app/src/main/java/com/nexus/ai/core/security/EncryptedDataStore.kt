package com.nexus.ai.core.security

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.flow.Flow
import kotlinx.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStoreX by preferencesDataStore(name = "nexus_secure_props")

@Singleton
class EncryptedDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cryptoEngine: CryptographicEngine
) {
    fun getSecureString(keyName: String, defaultValue: String): Flow<String> {
        val targetKey = stringPreferencesKey(keyName)
        return context.dataStoreX.data.map { preferences ->
            val encryptedValue = preferences[targetKey]
            if (encryptedValue != null) {
                try { cryptoEngine.decryptData(encryptedValue) } catch (e: Exception) { defaultValue }
            } else {
                defaultValue
            }
        }
    }

    suspend fun putSecureString(keyName: String, value: String) {
        val targetKey = stringPreferencesKey(keyName)
        val encryptedValue = cryptoEngine.encryptData(value)
        context.dataStoreX.edit { preferences ->
            preferences[targetKey] = encryptedValue
        }
    }
}
