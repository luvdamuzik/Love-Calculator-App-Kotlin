package com.example.android.navigation.data.RandomUsers.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    val results: List<SingleUser>?,
    val info: Info?
): Parcelable