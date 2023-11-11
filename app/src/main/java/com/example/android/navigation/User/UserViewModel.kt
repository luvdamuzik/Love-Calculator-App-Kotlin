package com.example.android.navigation.User

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.navigation.data.RandomUsers.models.User
import com.example.android.navigation.data.RandomUsers.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val UserEventChannel = Channel<UserEvent>()
    val savedArticleEvent = UserEventChannel.receiveAsFlow()

    fun getAllUsers() = userRepository.getAllUsers()

    fun onUndoDeleteClick(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    sealed class UserEvent{
        data class ShowUndoDeleteUserMessage(val user: User): UserEvent()
    }
}