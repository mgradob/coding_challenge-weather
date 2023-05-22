package com.mgb.codingchallenge.weather.utils

import android.content.SharedPreferences

private const val KEY_LAST_SEARCHED = "KEY_LAST_SEARCHED"

fun SharedPreferences.getLastSearched(): String? = getString(KEY_LAST_SEARCHED, null)

fun SharedPreferences.setLastSearched(search: String) = with(edit()) {
    putString(KEY_LAST_SEARCHED, search)
    apply()
}
