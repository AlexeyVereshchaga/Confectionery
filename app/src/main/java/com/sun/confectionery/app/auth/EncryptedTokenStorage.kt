package com.sun.confectionery.app.auth

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sun.confectionery.core.auth.TokenStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.security.KeyStore
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class EncryptedTokenStorage(private val context: Context) : TokenStorage {

    private val Context.dataStore by preferencesDataStore("secure_store")

    private val keyAccessToken = stringPreferencesKey("access_token")
    private val keyRefreshToken = stringPreferencesKey("refresh_token")


    override suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[keyAccessToken] = encrypt(token)
        }
    }

    override suspend fun saveRefreshToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[keyRefreshToken] = encrypt(token)
        }
    }

    override suspend fun getAccessToken(): String? {
        return accessToken.firstOrNull()
    }

    override suspend fun getRefreshToken(): String? {
        return refreshToken.firstOrNull()
    }

    override suspend fun clear() {
        context.dataStore.edit { prefs ->
            prefs.remove(keyAccessToken)
            prefs.remove(keyRefreshToken)
        }
    }

    val accessToken: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[keyAccessToken]?.let { decrypt(it) } }

    val refreshToken: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[keyRefreshToken]?.let { decrypt(it) } }

    private fun encrypt(plaintext: String): String {
        try {
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val secretKey = getOrCreateSecretKey()
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)

            val iv = cipher.iv
            val encryptedBytes = cipher.doFinal(plaintext.toByteArray(Charsets.UTF_8))
            val combined = iv + encryptedBytes

            return Base64.getEncoder().encodeToString(combined)
        } catch (e: Exception) {
            throw SecurityException("Failed to encrypt token", e)
        }
    }

    private fun decrypt(encryptedData: String): String {
        try {
            val combined = Base64.getDecoder().decode(encryptedData)
            val iv = combined.copyOfRange(0, 12) // GCM IV size
            val encryptedBytes = combined.copyOfRange(12, combined.size)

            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val secretKey = getOrCreateSecretKey()
            val spec = GCMParameterSpec(128, iv)

            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
            val decryptedBytes = cipher.doFinal(encryptedBytes)

            return String(decryptedBytes, Charsets.UTF_8)
        } catch (e: Exception) {
            throw SecurityException("Failed to decrypt token", e)
        }
    }

    private fun getOrCreateSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }

        return if (keyStore.containsAlias(KEY_ALIAS)) {
            (keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry).secretKey
        } else {
            createSecretKey()
        }
    }

    private fun createSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore"
        )

        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).apply {
            setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            setKeySize(256)
        }.build()

        keyGenerator.init(keyGenParameterSpec)
        return keyGenerator.generateKey()
    }

    companion object {
        private const val KEY_ALIAS = "token_encryption_key"
    }
}