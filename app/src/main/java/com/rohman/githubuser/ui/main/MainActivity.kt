package com.rohman.githubuser.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rohman.githubuser.R
import com.rohman.githubuser.data.response.ItemsItem
import com.rohman.githubuser.databinding.ActivityMainBinding
import com.rohman.githubuser.ui.adapter.Adapter
import com.rohman.githubuser.ui.favorite.FavoriteActivity
import com.rohman.githubuser.ui.setting.SettingActivity
import com.rohman.githubuser.ui.setting.SettingPreferences
import com.rohman.githubuser.ui.setting.SettingViewModel
import com.rohman.githubuser.ui.setting.SettingViewModelFactory


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Github User"

        initTheme()

        mainViewModel.users.observe(this){user ->
            if (user != null)
                setUsers(user)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{ _, _, _->
                    searchBar.text =searchView.text
                    searchView.hide()
                    mainViewModel.findUsers(searchView.text.toString())
                    false
                }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun setUsers(users: ArrayList<ItemsItem>) {
        binding.rvUsers.adapter = Adapter(users)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
    }

    private fun initTheme() {
        val settingPreferences = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(settingPreferences))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }


}