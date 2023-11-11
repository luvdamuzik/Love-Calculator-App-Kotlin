package com.example.android.navigation.Add

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.navigation.NetworkUtil.Companion.hasInternetConnection
import com.example.android.navigation.Resource
import com.example.android.navigation.data.RandomUsers.models.User
import com.example.android.navigation.data.RandomUsers.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var user: MutableLiveData<Resource<User>> = MutableLiveData()
    private val userEventChannel = Channel<UserEvent>()

    init {
        getUser()
    }

    fun getUser() = viewModelScope.launch {
        safeUserCall()
    }

    private suspend fun safeUserCall(){
        user.postValue(Resource.Loading())
        try{
            if(hasInternetConnection(context)){
                val response = userRepository.getUser()
                user.postValue(handleUserResponse(response))
            }
            else{
                user.postValue(Resource.Error("No Internet Connection"))
            }
        }
        catch (ex : Exception){
            when(ex){
                is IOException -> user.postValue(Resource.Error("Network Failure"))
                else -> user.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleUserResponse(response: Response<User>): Resource<User> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
            userEventChannel.send(UserEvent.ShowUserMessage("Article Saved."))
        }
    }
    sealed class UserEvent{
        data class ShowUserMessage(val message: String): UserEvent()
    }
}