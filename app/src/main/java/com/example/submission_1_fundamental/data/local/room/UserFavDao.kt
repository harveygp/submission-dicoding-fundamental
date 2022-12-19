package com.example.submission_1_fundamental.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submission_1_fundamental.data.local.entity.UserFavEntity

@Dao
interface UserFavDao {

    @Query("SELECT * FROM userFav ORDER BY id DESC")
    fun getFavUser(): LiveData<List<UserFavEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserFav(user: UserFavEntity)

    @Query("SELECT EXISTS(SELECT * FROM userFav WHERE login = :login)")
    suspend fun isUserFav(login: String): Boolean

    @Query("DELETE FROM userFav WHERE login = :login")
    suspend fun deleteUserFav(login: String)

}