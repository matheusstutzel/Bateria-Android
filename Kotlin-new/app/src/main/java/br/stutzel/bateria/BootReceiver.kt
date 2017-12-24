package br.stutzel.bateria

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        val i = Intent(context, Monitor::class.java)
        if (((Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) && (Intent.ACTION_LOCKED_BOOT_COMPLETED == action)) || (
                Intent.ACTION_BOOT_COMPLETED == action)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(i)
            } else context.startService(i)
        }
    }
}
