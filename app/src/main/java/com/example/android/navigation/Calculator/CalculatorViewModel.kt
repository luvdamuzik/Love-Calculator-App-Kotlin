package com.example.android.navigation.Calculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.navigation.Add.AddViewModel
import com.example.android.navigation.FIRST_NAME
import com.example.android.navigation.SECOND_NAME
import com.example.android.navigation.data.LoveCalculator.Love
import com.example.android.navigation.data.LoveCalculator.LoveRepository
import com.example.android.navigation.data.RandomUsers.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val loveRepository: LoveRepository
) : ViewModel() {

    val array_test: MutableList<String> = mutableListOf()
    val array_pictures:MutableList<String> = mutableListOf()
    var selected_item1: MutableLiveData<String> = MutableLiveData()
    var selected_item_picture1: String = ""
    var selected_item2: MutableLiveData<String> = MutableLiveData()
    var selected_item_picture2: String = ""
    var love:MutableLiveData<Love?> =  MutableLiveData()

    fun getAllUsers() = userRepository.getAllUsers()

    fun showButton(): Boolean {
        if(selected_item1.value != null && selected_item2.value != null)
            return true;
        return false
    }

    fun setParameters1(selectedItemText : String){
        val index = array_test.indexOf(selectedItemText)
        if(selected_item1.value != "") {
            selected_item1.value?.let { array_test.add(it) }
        }
        if(selected_item_picture1 != ""){
            selected_item_picture1.let{ array_pictures.add(it)}
        }
        selected_item_picture1 = array_pictures[index]
        selected_item1.value = selectedItemText
        array_test.remove(selectedItemText)
        array_pictures.removeAt(index)
        FIRST_NAME = selected_item1.value!!
    }


    fun setParameters2(selectedItemText : String){
        val index = array_test.indexOf(selectedItemText)
        if(selected_item2.value != "") {
            selected_item2.value?.let { array_test.add(it) }
        }
        if(selected_item_picture2 != ""){
            selected_item_picture2.let{ array_pictures.add(it)}
        }
        selected_item_picture2 = array_pictures[index]
        selected_item2.value = selectedItemText
        array_test.remove(selectedItemText)
        array_pictures.removeAt(index)
        SECOND_NAME = selected_item2.value!!
    }

    fun saveLove(){
        viewModelScope.launch {
            love.value = loveRepository.getLove().body()
            if (love.value != null) {
                love.value?.let { loveRepository.insertLove(it) }
            }

        }
    }
}