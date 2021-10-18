package ru.kostry.weather.model.repository

import ru.kostry.weather.model.dto.WeatherDTO

//реализация
class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {
    override fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: retrofit2.Callback<WeatherDTO>
    ) {
        //отправляем запрос на сервер
        remoteDataSource.getWeatherDetails(lat, lon, callback)
    }
}