package com.rohman.githubuser.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rohman.githubuser.data.response.ItemsItem
import com.rohman.githubuser.databinding.ActivityMainBinding
import com.rohman.githubuser.ui.adapter.Adapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                .setOnEditorActionListener{ textView, actionId, event->
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

}