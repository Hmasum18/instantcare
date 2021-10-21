package com.memoryleak.instantcare.view;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class PowerButtonService extends Service {
    private static final String TAG = "PowerButtonService";


    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: ");
            if(Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
                Log.d(TAG, "onReceive: power button clicked for turning off the screen");
            }else if(Intent.ACTION_SCREEN_ON.equals(intent.getAction())){
                Log.d(TAG, "onReceive: power button is clicked for screen on");
            }
        }
    };
    

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int value = intent.getIntExtra("POWER_BUTTON_CLICKED", 0);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setContentTitle("Emergency service running")
                .setContentText("clicker power button 3 times")
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: power button service started.");
        // register receiver that handles screen on and screen off logic
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);

        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: power button service destroyed.");
        unregisterReceiver(mReceiver);
       /* if(Build.VERSION.SDK_INT<Build.VERSION_CODES.O)
            startService(new Intent(this, PowerButtonService.class));
        else
            startForegroundService(new Intent(this, PowerButtonService.class));*/
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
