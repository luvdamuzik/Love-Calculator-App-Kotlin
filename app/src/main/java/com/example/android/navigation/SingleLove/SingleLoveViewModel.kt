package com.example.android.navigation.SingleLove

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.navigation.data.LoveCalculator.LoveRepository
import com.example.android.navigation.data.RandomUsers.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleLoveViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var pic1:MutableLiveData<String> = MutableLiveData()
    var pic2:MutableLiveData<String> = MutableLiveData()

    fun getAllUsers() = userRepository.getAllUsers()

    fun PlacePic1(pic:String){
        pic1.value = pic
    }

    fun PlacePic2(pic:String){
        pic2.value = pic
    }
}