package com.example.android.navigation.data.LoveCalculator

import com.example.android.navigation.API_KEY_LOVE
import com.example.android.navigation.FIRST_NAME
import com.example.android.navigation.HOST_LOVE
import com.example.android.navigation.SECOND_NAME
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LoveApi {

    @GET("getPercentage")
    suspend fun getLove(
        @Query("sname") sname:String = SECOND_NAME,
        @Query("fname") fname:String = FIRST_NAME,
        @Query("rapidapi-key") apiKey: String = API_KEY_LOVE,
        @Query("rapidapi-host") host: String = HOST_LOVE
    ):Response<Love>

}