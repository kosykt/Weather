package ru.kostry.weather.model.repository

import ru.kostry.weather.model.data.Weather

interface Repository {
    //интерфесы которые имплементируются
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}