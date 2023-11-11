package com.example.android.navigation.data.RandomUsers

import com.example.android.navigation.API_KEY_USERS
import com.example.android.navigation.HOST_USERS
import com.example.android.navigation.data.RandomUsers.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("getuser")
    suspend fun getUser(
        @Query("rapidapi-key") apiKey: String = API_KEY_USERS,
        @Query("rapidapi-host") host: String = HOST_USERS
    ):Response<User>

}