package ru.kostry.weather.model.repository

import ru.kostry.weather.model.data.Weather

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
}