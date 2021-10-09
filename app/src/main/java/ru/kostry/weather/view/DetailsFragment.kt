package ru.kostry.weather.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import okhttp3.*
import ru.kostry.weather.BuildConfig
import ru.kostry.weather.R
import ru.kostry.weather.databinding.DetailsFragmentBinding
import ru.kostry.weather.model.data.Weather
import ru.kostry.weather.model.dto.WeatherDTO
import java.io.IOException

private const val TEMP_INVALID = -100
private const val FEELS_LIKE_INVALID = -100
private const val PROCESS_ERROR = "Обработка ошибки"
private const val REQUEST_API_KEY = "X-Yandex-API-Key"

class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle: Weather

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
        weatherBundle = arguments?.getParcelable<Weather>(BUNDLE_EXTRA) ?: Weather()
        getWeather()
    }

    private fun getWeather() {
        binding.main.hide()
        binding.loadingLayout.show()

        val client = OkHttpClient()
        val builder = Request.Builder()
        builder.header(REQUEST_API_KEY, BuildConfig.WEATHER_API_KEY)

        builder.url("https://api.weather.yandex.ru/v2/informers?lat=${weatherBundle.city.lat}&lon=${weatherBundle.city.lon}")
        val request = builder.build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback{

            val handler = Handler(Looper.myLooper()!!)

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val serverResponse: String? = response.body()?.string()
                if(response.isSuccessful && serverResponse != null){
                    handler.post{
                        renderData(Gson().fromJson(serverResponse, WeatherDTO::class.java))
                    }
                } else {
                    TODO(PROCESS_ERROR)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                TODO(PROCESS_ERROR)
            }
        })
    }

    private fun renderData(weatherDTO: WeatherDTO) {
        binding.main.show()
        binding.loadingLayout.hide()

        val fact = weatherDTO.fact
        val temp = fact!!.temp
        val feelsLike = fact.feels_like
        val condition = fact.condition
        if (temp == TEMP_INVALID || feelsLike == FEELS_LIKE_INVALID || condition == null) {
            TODO(PROCESS_ERROR)
        } else {
            val city = weatherBundle.city
            binding.cityName.text = city.city
            binding.cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                city.lat.toString(),
                city.lon.toString()
            )
            binding.temperatureValue.text = temp.toString()
            binding.feelsLikeValue.text = feelsLike.toString()
            binding.weatherCondition.text = condition
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