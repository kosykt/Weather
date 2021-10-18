package ru.kostry.weather.model.repository

import retrofit2.Call
import retrofit2.http.*
import ru.kostry.weather.model.dto.WeatherDTO

private const val REQUEST_API_KEY = "X-Yandex-API-Key"

//Этим интерфейсом мы описываем конкретный запрос на сервер
interface WeatherAPI {
    //"v2/informers" прибавляется к базовому API, т.е. это своего рода хвост
    //https://api.weather.yandex.ru/v2/informers
    @GET("v2/informers")
    fun getWeather(
        @Header(REQUEST_API_KEY) token: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>
    //Возвращает метод уже готовый класс с ответом от сервера (WeatherDTO)
}