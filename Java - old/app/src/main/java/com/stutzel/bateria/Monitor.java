package com.stutzel.bateria;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class Monitor extends Service {

    private static final int Id = 16111994;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int batteryPct = (int) ((level / (float) scale) * 100);
            notificacao(batteryPct);
        }
    };


    public Monitor() {
    }

    private void notificacao(int level) {
        mBuilder.setSmallIcon(getResources().getIdentifier("b" + level, "drawable", "com.stutzel.bateria"))
                .setContentText("Seu nível de bateria é: " + level + "%");
        mNotificationManager.notify(Id, mBuilder.build());
    }

    private void criaNotificacao() {
        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Bateria")
                .setContentText("Seu nível de bateria é:")
                .setOngoing(true)
                .setPriority(-1);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Bateria.class), 0);

        mBuilder.setAutoCancel(false);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(Id, mBuilder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        criaNotificacao();
        registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    public void onDestroy() {
        unregisterReceiver(this.mBatInfoReceiver);
        mNotificationManager.cancel(Id);
    }
}
