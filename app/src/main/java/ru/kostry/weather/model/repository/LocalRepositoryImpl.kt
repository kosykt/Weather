package ru.kostry.weather.model.repository

import ru.kostry.weather.model.data.Weather
import ru.kostry.weather.model.data.convertHistoryEntityToWeather
import ru.kostry.weather.model.data.convertWeatherToEntity

import ru.kostry.weather.room.HistoryDao

//В качестве источника данных воспользуемся DAO. Так как DAO работает с Entity, надо написать два
//дополнительных метода для конвертации данных в DataUtils/Mapping

class LocalRepositoryImpl(private val localDataSource: HistoryDao) : LocalRepository {
    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        return localDataSource.insert(convertWeatherToEntity(weather))
    }
}