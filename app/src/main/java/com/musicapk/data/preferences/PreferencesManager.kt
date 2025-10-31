package com.musicapk.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    
    companion object {
        private const val PREF_NAME = "music_apk_prefs"
        private const val KEY_FIRST_LAUNCH = "is_first_launch"
    }
    
    /**
     * Check if this is the first time the app is being launched
     */
    fun isFirstLaunch(): Boolean {
        return sharedPreferences.getBoolean(KEY_FIRST_LAUNCH, true)
    }
    
    /**
     * Mark that the app has been launched at least once
     */
    fun setFirstLaunchComplete() {
        sharedPreferences.edit()
            .putBoolean(KEY_FIRST_LAUNCH, false)
            .apply()
    }
}







