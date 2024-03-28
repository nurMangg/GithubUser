package com.rohman.githubuser.ui.splashscreen

import android.annotation.SuppressLint
import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.rohman.githubuser.R
import com.rohman.githubuser.ui.main.MainActivity
import com.rohman.githubuser.ui.setting.SettingPreferences
import com.rohman.githubuser.ui.setting.SettingViewModel
import com.rohman.githubuser.ui.setting.SettingViewModelFactory


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_DELAY)

        val settingPreferences = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(settingPreferences))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                val imageView = findViewById<ImageView>(R.id.github_image)
                imageView.setImageResource(R.drawable.github_light)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                val imageView = findViewById<ImageView>(R.id.github_image)
                imageView.setImageResource(R.drawable.github)
            }
        }


    }

    companion object {
        private const val SPLASH_DELAY = 3000L
    }
}