package com.example.android.navigation.data.RandomUsers

import com.example.android.navigation.data.RandomUsers.models.User
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApi: UserApi,
    private val userDao: UserDao
) {

    suspend fun getUser(): Response<User> {
        return userApi.getUser()
    }

    fun getAllUsers() = userDao.getUsers()

    suspend fun insertUser(user: User) = userDao.insert(user)

    suspend fun deleteUser(user: User) = userDao.delete(user)

    suspend fun deleteAllUsers() = userDao.deleteAllUsers()
}