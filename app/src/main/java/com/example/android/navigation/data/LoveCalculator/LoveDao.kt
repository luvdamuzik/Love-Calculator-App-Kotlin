package com.example.android.navigation.data.LoveCalculator

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LoveDao {

    @Query("SELECT * FROM love_table")
    fun getLove() : LiveData<List<Love>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(love: Love)

    @Delete
    suspend fun delete(love: Love)

    @Query("DELETE FROM love_table")
    suspend fun deleteAllLove()

    @Query("DELETE FROM love_table WHERE fname =:name OR sname=:name ")
    suspend fun deleteLoveByName(name:String)

}