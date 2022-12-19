package com.example.submission_1_fundamental.data.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.submission_1_fundamental.data.local.entity.UserFavEntity
import com.example.submission_1_fundamental.data.local.room.UserFavDao
import com.example.submission_1_fundamental.data.remote.response.ResponseUser
import com.example.submission_1_fundamental.data.remote.retrofit.ApiService
import com.example.submission_1_fundamental.domain.repository.UserRepository
import com.example.submission_1_fundamental.data.remote.response.UserDetail
import com.example.submission_1_fundamental.data.remote.response.UserFollow
import com.example.submission_1_fundamental.utils.Condition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api : ApiService,
    private val userFavDao : UserFavDao
) : UserRepository{

    override suspend fun getListSearchUser(q: String): Flow<Condition<ResponseUser>> = flow {
        try {
            emit(Condition.Loading())
            val users = api.getListSearchUser(q = q)
            emit(Condition.Success(users))
        }catch (e : HttpException){
            emit(Condition.Error(e.localizedMessage ?: "An Error Occurred"))
        }
    }

    override suspend fun getUser(username: String): Flow<Condition<UserDetail>> = flow {
        try {
            emit(Condition.Loading())
            val users = api.getUser(username)
            emit(Condition.Success(users))
        }catch (e : HttpException){
            emit(Condition.Error(e.localizedMessage ?: "An Error Occurred"))
        }
    }

    override suspend fun getUserFollowing(username: String): Flow<Condition<List<UserFollow>>> = flow {
        try {
            emit(Condition.Loading())
            val users = api.getUserFollowing(username)
            emit(Condition.Success(users))
        }catch (e : HttpException){
            emit(Condition.Error(e.localizedMessage ?: "An Error Occurred"))
        }
    }

    override suspend fun getUserFollowers(username: String): Flow<Condition<List<UserFollow>>> = flow {
        try {
            emit(Condition.Loading())
            val users = api.getUserFollowers(username)
            emit(Condition.Success(users))
        }catch (e : HttpException){
            emit(Condition.Error(e.localizedMessage ?: "An Error Occurred"))
        }
    }

    override suspend fun setUserFavorite(user : LiveData<UserDetail>){
        Log.d("Fav", "${userFavDao.isUserFav(user.value?.login.toString())}")
        if(!userFavDao.isUserFav(user.value?.login.toString())){
            val newUserFav = user.let {
                UserFavEntity(
                    it.value?.id,
                    it.value?.avatarUrl,
                    it.value?.login
                )
            }
            userFavDao.insertUserFav(newUserFav)
            Log.d("Fav", "Masuk Favorite")
        } else{
            userFavDao.deleteUserFav(user.value?.login.toString())
            Log.d("notFav", "keluar Favorite")
        }
    }

    override fun getUserFav() : LiveData<List<UserFavEntity>> = userFavDao.getFavUser()

}