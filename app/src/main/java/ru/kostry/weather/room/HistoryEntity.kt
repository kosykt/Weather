package ru.kostry.weather.room

import androidx.room.*

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)//ключ сам генерируется
    val id: Long,
    val city: String,
    val temperature: Int,
    val condition: String
)
