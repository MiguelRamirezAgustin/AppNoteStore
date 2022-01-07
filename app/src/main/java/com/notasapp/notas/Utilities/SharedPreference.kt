package com.notasapp.notas.Utilities

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(val context: Context) {

    private val PREFERENCE_NAME = "com_encuesta_xcaret"
    val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun saveString(KEY_NAME: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, value)
        editor.commit()
    }
}