package br.stutzel.bateria

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import java.lang.ref.WeakReference

class BatteryReceiver(monitor: Monitor) : BroadcastReceiver() {
    private val m = WeakReference<Monitor>(monitor)

    override fun onReceive(context: Context, intent: Intent) {
        val batteryPct: Int
        batteryPct = if (intent != null) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            ((level * 100.0) / scale).toInt()
        } else -1
        m.get()?.updateNotification(batteryPct)
    }
}
