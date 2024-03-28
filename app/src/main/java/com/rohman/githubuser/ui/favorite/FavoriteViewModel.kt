package com.rohman.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rohman.githubuser.data.local.FavoriteUser
import com.rohman.githubuser.data.repository.FavoriteUserRepo

class FavoriteViewModel(application: Application) : ViewModel() {
    private val favoriteUserRepo: FavoriteUserRepo = FavoriteUserRepo(application)
    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> = favoriteUserRepo.getFavoriteUsers()

}