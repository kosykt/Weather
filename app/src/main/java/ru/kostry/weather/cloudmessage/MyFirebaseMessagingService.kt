package ru.kostry.weather.cloudmessage

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.kostry.weather.R

//идентификатор канала
public const val CHANNEL_ID = "CHANNEL_MAIN"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    //var изменяя ИД помогает не заменять старые сообщения на новые, а показывать их оба
    private var notificationId = 10

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
        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID).apply {
            //установить иконку, тайтл, текст
            setSmallIcon(R.drawable.ic_kotlin_logo)
            setContentTitle(title)
            setContentText(message)
            //нужен для старых версий
            priority = NotificationCompat.PRIORITY_DEFAULT
        }

        //получть NotificationManager из Context.NOTIFICATION_SERVICE
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //изменить счетчик ИД
        notificationId++

        //построить сообщение
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    //сохранить и отправить на сервер
    override fun onNewToken(token: String) {
        Log.d("FIREBASEMSG", token)
    }
}