package com.example.android.navigation.data.RandomUsers.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SingleUser(
    val gender:String,
    val name: Name,
    val location: Location,
    val email:String,
    val dob: Dob,
    val phone:String,
    val cell:String,
    val picture: Picture,
    val nat:String
):Parcelable