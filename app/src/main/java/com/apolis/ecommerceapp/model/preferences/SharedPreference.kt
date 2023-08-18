package com.apolis.ecommerceapp.model.preferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveName(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getName(key: String, defaultValue: String? = null): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun clearName() {
        sharedPreferences.edit().remove("userName").apply()
    }

    fun saveNumber(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getNumber(key: String, defaultValue: String? = null): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun clearNumber() {
        sharedPreferences.edit().remove("userNumber").apply()
    }

    fun saveId(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getId(key: String, defaultValue: String? = null): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun clearId() {
        sharedPreferences.edit().remove("userId").apply()
    }

    fun saveEmail(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getEmail(key: String, defaultValue: String? = null): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun clearEmail() {
        sharedPreferences.edit().remove("email").apply()
    }

    fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "MySharedPreferences"
    }
}