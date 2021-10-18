package ru.kostry.weather.model.repository

import ru.kostry.weather.model.data.Weather
import ru.kostry.weather.model.data.getRussianCities
import ru.kostry.weather.model.data.getWorldCities

class RepositoryImpl : Repository {
    //возвращают списки городов в MainViewModel
    override fun getWeatherFromServer() = Weather()
    override fun getWeatherFromLocalStorageRus() = getRussianCities()
    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
}