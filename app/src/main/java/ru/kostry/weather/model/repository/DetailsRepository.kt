package ru.kostry.weather.model.repository

import ru.kostry.weather.model.dto.WeatherDTO

//обозначает работу с данными с DetailsFragment'a
//метод, который принимает в качестве аргументов  callback помимо callback он принимает долготу и широту
interface DetailsRepository {
    fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: retrofit2.Callback<WeatherDTO>
    )
}