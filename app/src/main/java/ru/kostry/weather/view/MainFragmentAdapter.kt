package ru.kostry.weather.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kostry.weather.databinding.MainRecyclerItemBinding
import ru.kostry.weather.model.data.Weather

class MainFragmentAdapter :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    //источник данных
    private var weatherData: List<Weather> = listOf()
    //
    private var onItemViewClickListener: (Weather) -> Unit = {}

    fun setOnItemViewClickListener(onItemViewClickListener: (Weather) -> Unit) {
        this.onItemViewClickListener = onItemViewClickListener
    }

    //2установить данные
    fun setWeather(data: List<Weather>) {
        weatherData = data
        //после установки данных, вызов изменений списка в xml
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        //3привязываем итем и передаем в MainViewHolder
        val binding = MainRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    //указывается позиция
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        //4передаем данные и его позицию
        holder.bind(weatherData[position])
    }

    //1возвращаем колличество/размер
    override fun getItemCount(): Int {
        return weatherData.size
    }

    inner class MainViewHolder(val binding: MainRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //5привязываем погоду
        fun bind(weather: Weather) {
            binding.apply {
                //приравнивие TextView к названию города - это и дает список городов
                mainFragmentRecyclerItemTextView.text = weather.city.city
                root.setOnClickListener {
                    onItemViewClickListener(weather)
                }
            }
        }
    }
}