package com.example.submission_1_fundamental.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission_1_fundamental.data.local.entity.UserFavEntity

@Database(entities = [UserFavEntity::class], version = 1, exportSchema = false)
abstract class UserFavDatabase : RoomDatabase() {

    abstract fun userFavDao(): UserFavDao

    companion object {
        @Volatile
        private var instance: UserFavDatabase? = null
        fun getInstance(context: Context): UserFavDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    UserFavDatabase::class.java, "News.db"
                ).build()
            }
    }
}