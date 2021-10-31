package ru.kostry.weather.cloudmessage

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.kostry.weather.R

//идентификатор канала
private const val CHANNEL_ID = "CHANNEL_MAIN"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    //
    private val notificationId = 10

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        //получаем тайтл сообщения
        val title = remoteMessage.notification?.title ?: "Title"
        //получаем текст сообщения
        val body = remoteMessage.notification?.body ?: "Body"
        //показать сообщение
        showNotification(title, body)
    }

    //показать сообщение//нотификация между программами
    private fun showNotification(title: String, message: String) {
        //билдер создает канал
        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID).apply{
            //установить иконку, тайтл, текст
            setSmallIcon(R.drawable.ic_kotlin_logo)
            setContentTitle(title)
            setContentText(message)
            //нужен для старых версий
            priority = NotificationCompat.PRIORITY_DEFAULT
        }

        //получть NotificationManager из Context.NOTIFICATION_SERVICE
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //если версия новая, то открыть канал
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(notificationManager)
        }

        //построить сообщение
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    //создать новый канал
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "Channel name"
        val descriptionText = "Channel description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
            description = descriptionText
        }
        notificationManager.createNotificationChannel(channel)
    }

    //сохранить и отправить на сервер
    override fun onNewToken(token: String) {
        Log.d("FIREBASEMSG", token)
    }
}