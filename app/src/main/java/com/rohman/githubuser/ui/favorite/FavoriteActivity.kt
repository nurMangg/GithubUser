package com.rohman.githubuser.ui.favorite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rohman.githubuser.data.response.ItemsItem
import com.rohman.githubuser.databinding.ActivityFavoriteBinding
import com.rohman.githubuser.ui.adapter.FavoriteAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteUserViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Favorite User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initObserver()

    }

    private fun initObserver() {
        favoriteUserViewModel.getFavoriteUsers().observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val user = ItemsItem(it.login, it.avatarUrl.toString())
                items.add(user)
            }

            if (users.isEmpty()) {
                setFavoriteUsers(items)
            } else {
                setFavoriteUsers(items)
            }
        }
    }

    private fun setFavoriteUsers(favoriteUsers: ArrayList<ItemsItem>) {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = FavoriteAdapter(favoriteUsers)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}