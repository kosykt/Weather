package ru.kostry.weather.room

import androidx.room.Database
import androidx.room.RoomDatabase

//entities указывает какой класс применяется
//version указывает версию, когда вы меняете схему таблицы базы данных, вам придется увеличивать номер версии.
//exportSchema = false, чтобы не сохранять резервные копии истории версий схемы
@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
abstract class HistoryDataBase : RoomDatabase() {
    //получить HistoryDao
    abstract fun historyDao(): HistoryDao
}