package com.rohman.githubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.rohman.githubuser.data.local.FavoriteUser
import com.rohman.githubuser.data.local.FavoriteUserDao
import com.rohman.githubuser.data.local.FavoriteUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepo(application: Application) {

    private val favoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)

        favoriteUserDao = db.favoriteUserDao()
    }

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { favoriteUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: String) {
        executorService.execute { favoriteUserDao.delete(favoriteUser) }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> =
        favoriteUserDao.getFavoriteUserByUsername(username)

    fun getFavoriteUsers() = favoriteUserDao.getFavoriteUsers()



}