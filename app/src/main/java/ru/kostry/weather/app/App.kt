package ru.kostry.weather.app

import android.app.Application
import androidx.room.Room
import ru.kostry.weather.room.HistoryDao
import ru.kostry.weather.room.HistoryDataBase
import java.lang.IllegalStateException
//инициализация БД
//Создадим базу через паттерн Singleton
class App : Application() {

    //при запуске выполниться/запустится данный класс
    //надо в манифесте прописать android:name=".app.App"
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: HistoryDataBase? = null
        private const val DB_NAME = "History.db"

        //определим статический метод, который будет возвращать DAO.
        //Именно через DAO мы вносим данные в базу, удаляем или изменяем их.
        fun getHistoryDao(): HistoryDao {
            if (db == null) {
                //synchronized означает, что только один поток выполнения может одновременно входить в этот блок кода, что гарантирует, что база данных инициализируется только один раз
                synchronized(HistoryDataBase::class.java) {
                    //паттерн дабл чек для экономии ресурсов ANKles7 1.49.00 time
                    if (db == null) {
                        if (appInstance == null) {
                            throw IllegalStateException("Application ids null meanwhile creating database")
                        }
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            HistoryDataBase::class.java,
                            DB_NAME)
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return db!!.historyDao()
        }
    }
}
/*
в самом методе getHistoryDao():
если БД ещё не создана, то мы потокобезопасно формируем базу
через метод Room.databaseBuilder, который принимает три аргумента — контекст, база и имя БД. В
системе может храниться несколько баз с разными именами и структурами данных. Метод
allowMainThreadQueries позволяет делать запросы из основного потока.
Важно! К БД запрещено обращаться в основном потоке. Этот метод используется
исключительно в качестве примера или тестирования. Вы уже знаете, как делать вызовы в
отдельных потоках, и это будет вашим практическим заданием.
 */