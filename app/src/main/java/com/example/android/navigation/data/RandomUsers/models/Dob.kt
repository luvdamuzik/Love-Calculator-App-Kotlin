package com.example.android.navigation.data.RandomUsers.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dob(
    val date:String,
    val age:String
):Parcelable