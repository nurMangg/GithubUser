package com.rohman.githubuser.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite_users")
data class FavoriteUser(
    @PrimaryKey(autoGenerate = false)
    var login: String = "",
    var avatarUrl: String? = null,
): Parcelable
