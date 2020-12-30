package com.android.timesheetchecker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.android.timesheetchecker.di.DaggerAppComponent
import dagger.android.DaggerApplication

class TimesheetCheckerApplication : DaggerApplication() {

    override fun applicationInjector() = DaggerAppComponent.builder().application(this).build()

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel =
                NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
// or other notification behaviors after this
            val notificationManager =
                getSystemService(
                    NotificationManager::class.java
                )
            notificationManager?.createNotificationChannel(channel)
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "111"
    }
}
