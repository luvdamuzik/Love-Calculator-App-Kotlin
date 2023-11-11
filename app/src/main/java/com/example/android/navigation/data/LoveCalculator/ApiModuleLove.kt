package com.example.android.navigation.data.LoveCalculator


import com.example.android.navigation.BASE_URL_LOVE
import com.example.android.navigation.data.RandomUsers.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModuleLove {

    @Provides
    @Singleton
    @Named("Love")
    fun provideLoveRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_LOVE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoveApi(@Named("Love") retrofit: Retrofit): LoveApi {
        return retrofit.create(LoveApi::class.java)
    }
}