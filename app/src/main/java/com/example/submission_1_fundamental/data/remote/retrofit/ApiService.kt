package com.example.submission_1_fundamental.data.remote.retrofit

import com.example.submission_1_fundamental.common.Constant
import com.example.submission_1_fundamental.data.remote.response.ResponseUser
import com.example.submission_1_fundamental.data.remote.response.UserDetail
import com.example.submission_1_fundamental.data.remote.response.UserFollow
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization:token ${Constant.TOKEN}")
    @GET("search/users")
    suspend fun getListSearchUser(@Query("q") q: String): ResponseUser

    @Headers("Authorization:token ${Constant.TOKEN}")
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): UserDetail

    @Headers("Authorization:token ${Constant.TOKEN}")
    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String): List<UserFollow>

    @Headers("Authorization:token ${Constant.TOKEN}")
    @GET("users/{username}/followers")
    suspend fun getUserFollowers(@Path("username") username: String): List<UserFollow>

}