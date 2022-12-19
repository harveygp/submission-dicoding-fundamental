package com.example.submission_1_fundamental.data.remote.retrofit

import android.app.Application
import com.example.submission_1_fundamental.common.Constant
import com.example.submission_1_fundamental.data.local.room.UserFavDao
import com.example.submission_1_fundamental.data.local.room.UserFavDatabase
import com.example.submission_1_fundamental.data.repository.UserRepositoryImpl
import com.example.submission_1_fundamental.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiConfig {

        @Provides
        @Singleton
        fun provideApiService(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }

    @Provides
    @Singleton
    fun provideUserRepository(api : ApiService, app : UserFavDao) : UserRepository = UserRepositoryImpl(api, app)

    @Provides
    @Singleton
    fun getAppDatabase(context: Application): UserFavDatabase {
        return UserFavDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun getAppDao(appDatabase: UserFavDatabase): UserFavDao {
        return appDatabase.userFavDao()
    }


}