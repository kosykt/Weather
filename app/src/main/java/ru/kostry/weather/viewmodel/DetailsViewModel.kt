package ru.kostry.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.kostry.weather.app.App.Companion.getHistoryDao
import ru.kostry.weather.model.AppState
import ru.kostry.weather.model.data.Weather
import ru.kostry.weather.model.data.convertDtoToModel
import ru.kostry.weather.model.dto.FactDTO
import ru.kostry.weather.model.dto.WeatherDTO
import ru.kostry.weather.model.repository.*
import java.io.IOException

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsViewModel(
    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepository: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource()),
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {

    //вызывается фрагментом
    fun getWeatherFromRemoteSource(lat: Double, lon: Double) {
        detailsLiveData.value = AppState.Loading
        //отправляет запрос на сервер
        detailsRepository.getWeatherDetailsFromServer(lat, lon, callBack)
    }

    //сохранять новый запрос в БД
    fun saveCityToDB(weather: Weather) {
        historyRepository.saveEntity(weather)
    }

    //отправляет запрос на сервер
    private val callBack = object : Callback<WeatherDTO> {

        @Throws(IOException::class)
        override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
            //вытаскиваем body
            val serverResponse: WeatherDTO? = response.body()
            //передаем значение в main поток postValue
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    //возвращаем полученный с сервера список параметров
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }

    fun checkResponse(serverResponse: WeatherDTO): AppState {
        //приравниваем
        val fact: FactDTO? = serverResponse.fact
        return if (fact?.temp == null || fact.feels_like == null || fact.condition.isNullOrEmpty()) {
            AppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            AppState.Success(convertDtoToModel(serverResponse))
        }
    }
}