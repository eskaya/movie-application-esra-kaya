package com.eskaya.movie_application.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.eskaya.movie_application.R
import com.eskaya.movie_application.presentation.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

open class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.notification != null) {
            startNotification(message.notification!!.title!!, message.notification!!.body!!)
        }
    }

    @SuppressLint("RemoteViewLayout")
    private fun customDesignNotification(title: String, message: String): RemoteViews {
        val remoteViews = RemoteViews(applicationContext.packageName, R.layout.notification)
        remoteViews.setTextViewText(R.id.tvNotificationTitle,title)
        remoteViews.setTextViewText(R.id.tvMessage,message)
        remoteViews.setImageViewResource(R.id.ivNotification, R.drawable.ic_albert_einstein)
        return remoteViews
    }


    private fun startNotification(title: String, message: String) {
        val channelId = "notification_channel"
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_albert_einstein)
            .setAutoCancel(true) //tıklama sonrası bildirimi otomatik kapatma
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000)) //bildirim titreşim ayarları
            .setOnlyAlertOnce(true) //aynı bildirim yeniden gelirse sesi ve titreşimi kapat
            .setContentIntent(pendingIntent)

        builder = builder.setContent(customDesignNotification(title,message))

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId, "web_app",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(
            0,
            builder.build()
        ) //Notification nesnesini bildirim yöneticisine göndermek ve cihazın ekranına bu bildirimi görüntülemesi için bildirim yöneticisini tetiklemek

    }

}

