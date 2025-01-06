package com.premierdarkcoffee.sales.maia.root.util.helper

//
//  SecurePreferencesHelper.kt
//  Maia
//
//  Created by José Ruiz on 8/8/24.
//

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object JwtSecurePreferencesHelper {

    private const val PREFERENCE_NAME = "jwt_secure_preferences"
    private const val KEY_NAME = "jwt"

    private fun getSecurePreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        return EncryptedSharedPreferences.create(
            context,
            PREFERENCE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getJwt(context: Context): String? {
        return getSecurePreferences(context).getString(KEY_NAME, "")
    }

    fun setJwt(
        context: Context,
        jwt: String
    ) {
        getSecurePreferences(context).edit().putString(KEY_NAME, jwt).apply()
    }

    fun deleteJwt(context: Context) {
        getSecurePreferences(context).edit().remove(KEY_NAME).apply()
    }
}




