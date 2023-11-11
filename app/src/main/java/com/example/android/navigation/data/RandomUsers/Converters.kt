package com.example.android.navigation.data.RandomUsers

import androidx.room.TypeConverter
import com.example.android.navigation.data.RandomUsers.models.*

class Converters {
    @TypeConverter
    fun fromInfo(info: Info): String{
        return info.seed
    }

    @TypeConverter
    fun toInfo(info: String): Info {
        return Info(info, 0,0,info)
    }

    @TypeConverter
    fun fromListSingleUser(user: MutableList<SingleUser>): String{
        val vrati = user[0].gender + "," + user[0].name.title + "," + user[0].name.first + "," + user[0].name.last +
                "," + user[0].location.street.number + "," + user[0].location.street.name +"," + user[0].location.city +
                "," + user[0].location.state + "," + user[0].location.country + "," + user[0].location.postcode +
                "," + user[0].location.coordinates.latitude + "," + user[0].location.coordinates.longitude +
                "," + user[0].email + "," + user[0].dob.date + "," + user[0].dob.age + "," + user[0].phone +
                "," + user[0].cell + "," + user[0].picture.large + "," + user[0].picture.medium + "," + user[0].picture.thumbnail +
                "," + user[0].nat
        return vrati
    }

    @TypeConverter
    fun toListSingleUser(user: String): MutableList<SingleUser>{
        val lista = user.split(",")
        return arrayListOf(
            SingleUser(lista[0],
                Name(lista[1],lista[2],lista[3]),
            Location(Street(lista[4],lista[5]),lista[6],lista[7],lista[8],lista[9], Coordinates(lista[10],lista[11])),
            lista[12],
                Dob(lista[13],lista[14]), lista[15],lista[16], Picture(lista[17],lista[18],lista[19]),lista[20]
        )
        )
    }


}