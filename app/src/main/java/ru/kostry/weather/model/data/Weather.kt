package ru.kostry.weather.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//POJO класс, который автоматически созраняет свое состояние при помощи @Parcelize, так как Hashcode() и equals() переопределены в data классах
@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0,
    val condition: String = "sunny"
) : Parcelable

fun getDefaultCity() = City("Москва", 55.5578, 37.61729)