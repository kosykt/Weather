package ru.kostry.weather.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.*
import ru.kostry.weather.BuildConfig
import ru.kostry.weather.R
import ru.kostry.weather.databinding.DetailsFragmentBinding
import ru.kostry.weather.model.AppState
import ru.kostry.weather.model.data.Weather
import ru.kostry.weather.model.dto.WeatherDTO
import ru.kostry.weather.viewmodel.DetailsViewModel
import java.io.IOException

private const val TEMP_INVALID = -100
private const val FEELS_LIKE_INVALID = -100
private const val PROCESS_ERROR = "Обработка ошибки"
private const val REQUEST_API_KEY = "X-Yandex-API-Key"

class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    //записывает Bundle
    private lateinit var weatherBundle: Weather

    //привязываем ViewModel
    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //записавает погоду переданую в Bundle
        weatherBundle = arguments?.getParcelable<Weather>(BUNDLE_EXTRA) ?: Weather()
        //добавляем к ViewModel observe(отправляет/переписывает данные при их изменении)
        viewModel.detailsLiveData.observe(viewLifecycleOwner, { renderData(it) })
        //отправляем запрос в DetailsViewModel
        viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat, weatherBundle.city.lon)
    }

    //при изменении/отклике сервера
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.main.show()
                binding.loadingLayout.hide()
                setWeather(appState.weatherData[0])
            }
            is AppState.Loading -> {
                binding.main.hide()
                binding.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.main.show()
                binding.loadingLayout.hide()
                binding.main.showSnackBar(getString(R.string.error), getString(R.string.reload)) {
                    viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat, weatherBundle.city.lon)
                }
            }
        }
    }

    //вызывается при успехе
    private fun setWeather(weather: Weather) {
        with(binding) {
            weatherBundle.city.let { city ->
                cityName.text = city.city
                cityCoordinates.text = String.format(
                    getString(R.string.city_coordinates),
                    city.lat.toString(),
                    city.lon.toString()
                )
            }
            weather.let {
                temperatureValue.text = it.temperature.toString()
                feelsLikeValue.text = it.feelsLike.toString()
                weatherCondition.text = it.condition
            }
            //загрузить картинку
            Picasso
                .get()
                .load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
                    //это id в xml
                .into(headerIcon)
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}