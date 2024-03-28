package com.rohman.githubuser.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 2)
abstract class FavoriteUserDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao
    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteUserDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteUserDatabase::class.java, "users_favorite")
                        .build()
                }
            }
            return INSTANCE as FavoriteUserDatabase
        }
    }


}