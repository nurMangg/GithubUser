package com.rohman.githubuser.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Query("DELETE FROM favorite_users WHERE login = :favoriteUser")
    fun delete(favoriteUser: String)

    @Query("SELECT * FROM favorite_users WHERE login = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>

    @Query("SELECT * FROM favorite_users")
    fun getFavoriteUsers(): LiveData<List<FavoriteUser>>
}