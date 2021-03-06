package com.example.android.navigation.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.example.android.navigation.MainActivity
import com.example.android.navigation.R
import com.example.android.navigation.receiver.SnoozeReceiver

private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0


fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

    val contentIntent = Intent(applicationContext, MainActivity::class.java)

    val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )


    val eggImage = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.time
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(eggImage)
            .bigLargeIcon(null)


    val snoozeIntent = Intent(applicationContext, SnoozeReceiver::class.java)
    val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            REQUEST_CODE,
            snoozeIntent,
            FLAGS)



    val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.egg_notification_channel_id)
    )


            .setSmallIcon(R.drawable.ic_baseline_mood_bad_24)
            .setContentTitle(applicationContext
                    .getString(R.string.notification_title))
            .setContentText(messageBody)


            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)


            .setStyle(bigPicStyle)
            .setLargeIcon(eggImage)


            .addAction(
                    R.drawable.ic_baseline_mood_bad_24,
                    applicationContext.getString(R.string.snooze),
                    snoozePendingIntent
            )


            .setPriority(NotificationCompat.PRIORITY_HIGH)

    notify(NOTIFICATION_ID, builder.build())
}


fun NotificationManager.cancelNotifications() {
    cancelAll()
}
