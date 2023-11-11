package com.example.android.navigation.data.RandomUsers

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.navigation.data.RandomUsers.models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    fun getUsers() : LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

}