package com.example.android.navigation.SingleUser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.navigation.data.LoveCalculator.LoveRepository
import com.example.android.navigation.data.RandomUsers.models.User
import com.example.android.navigation.data.RandomUsers.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val loveRepository: LoveRepository
) : ViewModel() {

    private val userEventChannel = Channel<UserEvent>()
    val userEvent = userEventChannel.receiveAsFlow()

    fun deleteUser(user: User) {
        viewModelScope.launch {
            val user_name = user.results?.get(0)?.name?.first + " " + user.results?.get(0)?.name?.last
            userRepository.deleteUser(user)
            loveRepository.deleteLoveByName(user_name)
            userEventChannel.send(UserEvent.ShowUserMessage("User Deleted."))
        }
    }

    sealed class UserEvent{
        data class ShowUserMessage(val message: String): UserEvent()
    }
}