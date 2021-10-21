package ru.kostry.weather.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kostry.weather.databinding.HistoryRecyclerItemBinding
import ru.kostry.weather.model.data.Weather

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var data: List<Weather> = arrayListOf()

    //Метод setData добавляет данные для
    //отображения. Этот метод создан неслучайно: так как данные грузятся из БД асинхронно, то
    //RecyclerView и адаптер подготовятся гораздо раньше, чем появятся данные.
    fun setData(data: List<Weather>) {
        this.data = data
        //после установки данных, вызов изменений списка в xml
        //Когда мы у адаптера RecyclerView вызываем метод notifyDataSetChanged, то адаптер перерисует все элементы списка.
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val binding = HistoryRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class HistoryViewHolder(private val binding: HistoryRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data:Weather){
            binding.apply {
                cityName.text = data.city.city
                weatherCondition.text = data.condition
                weatherTemperature.text = data.temperature.toString()
            }
        }
    }
}