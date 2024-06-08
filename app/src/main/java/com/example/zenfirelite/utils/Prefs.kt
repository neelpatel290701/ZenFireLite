package com.example.zenfirelite.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.zenfirelite.utils.PREF_CONSTANTS.ACCESS_TOKEN
import com.example.zenfirelite.utils.PREF_CONSTANTS.COMPANY_ID
import com.example.zenfirelite.utils.PREF_CONSTANTS.USER_ID

class Prefs(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("ZTPref",Context.MODE_PRIVATE)

    var accessToken: String?
        get() = preferences.getString(ACCESS_TOKEN.value, null)
        set(value) {
            // Save the token to SharedPreferences
            preferences.edit().putString(ACCESS_TOKEN.value, value).apply()
        }

    var userID: String?
        get() = preferences.getString(USER_ID.value, null)
        set(value) {
            // Save the userId to SharedPreferences
            preferences.edit().putString(USER_ID.value, value).apply()
        }

    var companyID: String?
        get() = preferences.getString(COMPANY_ID.value, null)
        set(value) {
            // Save the userId to SharedPreferences
            preferences.edit().putString(COMPANY_ID.value, value).apply()
        }

    fun clear() {
        preferences.edit().clear().apply()
    }

}