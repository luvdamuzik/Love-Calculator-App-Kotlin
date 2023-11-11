package com.example.android.navigation.data.RandomUsers.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val street: Street,
    val city:String,
    val state:String,
    val country:String,
    val postcode:String,
    val coordinates: Coordinates
):Parcelable