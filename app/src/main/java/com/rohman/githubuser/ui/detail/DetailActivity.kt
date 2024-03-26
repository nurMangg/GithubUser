package com.rohman.githubuser.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rohman.githubuser.R
import com.rohman.githubuser.data.response.DetailResponseUser
import com.rohman.githubuser.data.response.ItemsItem
import com.rohman.githubuser.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel by viewModels<DetailViewModel>()
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val users = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<ItemsItem>(EXTRA_USER, ItemsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<ItemsItem>(EXTRA_USER)
        }
        if (users != null) {
            initObserver(users)
            initViewPager(users)
        }
    }
    private fun initViewPager(user : ItemsItem){
        val sectionsPagerAdapter = SectionsPagerAdapter(this,user.login)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter=sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs,viewPager) {tab, position ->
            tab.text= resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }
    private fun initObserver(user: ItemsItem) {
        detailViewModel.getUser(user.login)
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        detailViewModel.user.observe(this) {
            if (it != null) {
                setUserData(it)
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUserData(user: DetailResponseUser) {
        binding.apply {
            tvNameDetail.text=user.name
            tvUsernameDetail.text=user.login
            tvFollowers.text=user.followers.toString()
            tvFollowing.text=user.following.toString()
            tvRepository.text=user.publicRepos.toString()
            Glide.with(this@DetailActivity)
                .load(user.avatarUrl)
                .into(imgUserDetail)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
