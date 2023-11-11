package com.example.android.navigation.data.LoveCalculator

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.navigation.data.ApplicationScope
import com.example.android.navigation.data.RandomUsers.Converters
import com.example.android.navigation.data.RandomUsers.UserDao
import com.example.android.navigation.data.RandomUsers.models.User
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Love::class], version = 2)
//@TypeConverters(Converters::class)
abstract class LoveDatabase : RoomDatabase() {

    abstract fun getLoveDao(): LoveDao

    class Callback @Inject constructor(
        private val database: Provider<LoveDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()
}