package com.example.submission_1_fundamental.domain.repository

import androidx.lifecycle.LiveData
import com.example.submission_1_fundamental.data.local.entity.UserFavEntity
import com.example.submission_1_fundamental.data.remote.response.ResponseUser
import com.example.submission_1_fundamental.data.remote.response.UserDetail
import com.example.submission_1_fundamental.data.remote.response.UserFollow
import com.example.submission_1_fundamental.utils.Condition
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getListSearchUser(q: String) : Flow<Condition<ResponseUser>>

    suspend fun getUser(username: String) : Flow<Condition<UserDetail>>

    suspend fun getUserFollowing(username: String) : Flow<Condition<List<UserFollow>>>

    suspend fun getUserFollowers(username: String) : Flow<Condition<List<UserFollow>>>

    suspend fun setUserFavorite(user : LiveData<UserDetail>)

    fun getUserFav() : LiveData<List<UserFavEntity>>
}