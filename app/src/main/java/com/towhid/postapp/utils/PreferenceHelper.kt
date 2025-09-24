package com.towhid.postapp.utils

import android.content.Context
import androidx.core.content.edit

class Prefs(context: Context) {
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean = prefs.getBoolean("isLoggedIn", false)

    fun setLoggedIn(loggedIn: Boolean) {
        prefs.edit { putBoolean("isLoggedIn", loggedIn) }
    }

    fun clear() {
        prefs.edit { clear() }
    }
}
