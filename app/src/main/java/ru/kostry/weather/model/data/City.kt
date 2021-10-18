package ru.kostry.weather.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//POJO класс, который автоматически сохраняет свое состояние при помощи @Parcelize, так как Hashcode() и equals() переопределены в data классах
@Parcelize
data class City(
    val city: String,
    val lat: Double,
    val lon: Double
) : Parcelable