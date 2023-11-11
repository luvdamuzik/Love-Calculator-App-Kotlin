package com.example.android.navigation.Love

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.navigation.data.LoveCalculator.Love
import com.example.android.navigation.data.LoveCalculator.LoveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.receiveOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoveViewModel @Inject constructor(
    private val loveRepository: LoveRepository
) : ViewModel() {

    private val LoveEventChannel = Channel<LoveEvent>()
    val loveEvent = LoveEventChannel.receiveAsFlow()

    fun getAllLove() = loveRepository.getAllLove()

    fun onLoveSwiped(love: Love) {
        viewModelScope.launch {
            loveRepository.deleteLove(love)
            LoveEventChannel.send(LoveEvent.ShowUndoDeleteLoveMessage(love))
        }
    }

    fun onUndoDeleteClick(love: Love) {
        viewModelScope.launch {
            loveRepository.insertLove(love)
        }
    }

    sealed class LoveEvent{
        data class ShowUndoDeleteLoveMessage(val love: Love): LoveEvent()
    }
}