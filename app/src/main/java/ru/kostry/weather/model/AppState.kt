package ru.kostry.weather.model

import ru.kostry.weather.model.data.Weather

sealed class AppState {
    //состояния загрузки
    //Success - это список параметров, их и будем передавать
    data class Success(val weatherData: List<Weather>) : AppState()
    class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}