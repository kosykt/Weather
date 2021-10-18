package ru.kostry.weather.model.data

import ru.kostry.weather.model.dto.WeatherDTO

//принимает DTO, ктрый пришел с сервера и возвращает список
fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact = weatherDTO.fact!!
    return listOf(
        Weather(
            getDefaultCity(),
            fact.temp!!,
            fact.feels_like!!,
            fact.condition!!
        )
    )
}