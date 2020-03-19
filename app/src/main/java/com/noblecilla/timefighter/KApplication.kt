package com.noblecilla.timefighter

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class KApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val nightMode = when (Preferences.nightMode(this)) {
            Mode.LIGHT.ordinal -> AppCompatDelegate.MODE_NIGHT_NO
            Mode.NIGHT.ordinal -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}
