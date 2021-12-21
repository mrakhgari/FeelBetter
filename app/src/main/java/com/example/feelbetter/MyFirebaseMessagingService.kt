package com.example.feelbetter

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.feelbetter.activities.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("FCM", "refresh token: $token")
    }

    override fun onMessageReceived(rm: RemoteMessage) {
        // create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel(
                "MyNotification", "MyNotification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            var manager = getSystemService(NotificationManager::class.java) as NotificationManager;
            manager.createNotificationChannel(channel)
        }
        //log data
        Log.e(
            "TAG",
            "onMessageReceived: " + rm.data["title"] + rm.data["description"] + rm.data["body"],
        )
        showNotification(rm.data["title"], rm.data["description"])

        super.onMessageReceived(rm);
    }

    private fun showNotification(title: String?, desc: String?) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        var name = prefs.getString("signature", "");

        val notificationIntent =
            Intent(applicationContext, MainActivity::class.java)
        //create pending intent
        val contentIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        //create builder
        var builder = NotificationCompat.Builder(this, "MyNotification")
            .setContentTitle("Hi $name,$title")
            .setSmallIcon(R.drawable.image_2021_11_08_22_16_45)
            .setContentText(desc)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
        var manager = NotificationManagerCompat.from(this)
        manager.apply {
            notify(123, builder.build())
        }
    }
//    fun generateNotification(title: String, message: String) {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//
//        val pendingIntent =
//            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
//
//        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext)
//            .setSmallIcon(R.drawable.image_2021_11_08_22_16_45).setAutoCancel(true).setVibrate(
//                longArrayOf(1000, 1000, 1000, 1000)
//            ).setOnlyAlertOnce(true).setContentIntent(pendingIntent)
//
//        builder = builder.setContent(getRemoteView(title, message))
//    }
}