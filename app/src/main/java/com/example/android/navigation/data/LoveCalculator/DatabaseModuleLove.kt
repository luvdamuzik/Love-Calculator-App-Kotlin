package com.example.android.navigation.data.LoveCalculator

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModuleLove {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: LoveDatabase.Callback): LoveDatabase {
        return Room.databaseBuilder(application, LoveDatabase::class.java, "love_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideLoveDao(db: LoveDatabase): LoveDao {
        return db.getLoveDao()
    }
}