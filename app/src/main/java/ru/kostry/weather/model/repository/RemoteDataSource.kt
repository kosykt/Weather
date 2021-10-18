package ru.kostry.weather.model.repository

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kostry.weather.BuildConfig
import ru.kostry.weather.model.dto.WeatherDTO
//это класс, где происходит запрос на сервер
//Это источник данных
class RemoteDataSource {

    //создаем запрос
    private val weatherAPI = Retrofit.Builder()
        .baseUrl("https://api.weather.yandex.ru/")
            //атоматическое пробразование json в обьект
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        //создаем/прикрепляем созданный интерфейс create(WeatherAPI::class.java) к запросу
        ).build().create(WeatherAPI::class.java)

    fun getWeatherDetails(lat: Double, lon: Double, callback: Callback<WeatherDTO>) {
        //делаем запрос
        weatherAPI.getWeather(BuildConfig.WEATHER_API_KEY, lat, lon).enqueue(callback)
    }
}