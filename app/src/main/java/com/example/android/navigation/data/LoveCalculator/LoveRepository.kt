package com.example.android.navigation.data.LoveCalculator

import com.example.android.navigation.data.RandomUsers.UserApi
import com.example.android.navigation.data.RandomUsers.UserDao
import com.example.android.navigation.data.RandomUsers.models.User
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoveRepository @Inject constructor(
    private val loveApi: LoveApi,
    private val loveDao: LoveDao
) {

    suspend fun getLove(): Response<Love> {
        return loveApi.getLove()
    }

    fun getAllLove() = loveDao.getLove()

    suspend fun insertLove(love: Love) = loveDao.insert(love)

    suspend fun deleteLove(love: Love) = loveDao.delete(love)

    suspend fun deleteAllLove() = loveDao.deleteAllLove()

    suspend fun deleteLoveByName(name:String) =  loveDao.deleteLoveByName(name)
}