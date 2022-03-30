package com.vp.vpeasywidget.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class PreferenceUtils(context: Context) {

    private val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private var editor: SharedPreferences.Editor? = null

    fun setPreferences(key: String, value: String) {
        editor = pref.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    fun setPreferences(key: String, value: Int) {
        editor = pref.edit()
        editor?.putInt(key, value)
        editor?.apply()
    }

    fun setPreferences(key: String, value: Boolean) {
        editor = pref.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

    fun getPreference(key: String, Default: String = ""): String {
        return pref.getString(key, Default).toString()
    }

    fun getPreference(key: String, Default: Int = 0): Int {
        return pref.getInt(key, Default)
    }

    fun getPreference(key: String, Default: Boolean = false): Boolean {
        return pref.getBoolean(key, Default)
    }

    fun deletePref(key: Array<String>) {
        editor = pref.edit()
        for (i in key.indices)
            editor?.remove(key[i])
        editor?.apply()
    }

    fun deletePref(key: String) {
        editor = pref.edit()
        editor?.remove(key)
        editor?.apply()
    }
}