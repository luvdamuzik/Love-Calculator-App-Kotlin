package com.example.android.navigation.data.LoveCalculator

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "love_table")
data class Love(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    var fname: String?,
    var sname:String?,
    var percentage: Int?,
    var result:String?
): Parcelable