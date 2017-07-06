package br.stutzel.bateria

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.BatteryManager
import android.os.IBinder
import android.support.v4.app.NotificationCompat


class Monitor : Service() {
    var Id = 16111994
    private lateinit var mBuilder: NotificationCompat.Builder
    private lateinit var mNotificationManager: NotificationManager
    var mBatInfoReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val batteryPct: Int
            if (intent != null) {
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                batteryPct = ((level * 100.0) / scale).toInt()
            } else batteryPct = -1
            updateNotification(batteryPct)
        }

    }


    override fun onBind(intent: Intent): IBinder? = null
    override fun onCreate() {
        super.onCreate()
        buildNotification()
        registerReceiver(mBatInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBatInfoReceiver)
        cleanNotification()
    }

    private fun cleanNotification() {
        mNotificationManager.cancel(Id)
    }

    private fun buildNotification() {
        mBuilder = NotificationCompat.Builder(this)
        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
        mBuilder.setContentTitle(getString(R.string.notification_title))
        mBuilder.setContentText(getString(R.string.notification_text, 1))
        mBuilder.setOngoing(true)
        mBuilder.priority = -1
        mBuilder.setAutoCancel(false)
        mBuilder.setContentIntent(PendingIntent.getActivity(this, 0,
                Intent(this, MainActivity::class.java), 0))
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(Id, mBuilder.build())
    }

    private fun updateNotification(batteryPct: Int) {
        mBuilder
                .setSmallIcon(generateSmallIcon(batteryPct))
                .setColor(Color.BLACK)
                .setContentText(getString(R.string.notification_text, batteryPct))
        mNotificationManager.notify(Id, mBuilder.build())
    }

    private fun generateSmallIcon(batteryPct: Int): Int {
        return resources.getIdentifier("b" + batteryPct, "drawable", "br.stutzel.bateria")
    }
}
