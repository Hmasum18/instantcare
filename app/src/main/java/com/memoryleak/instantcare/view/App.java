package com.memoryleak.instantcare.view;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.memoryleak.instantcare.dagger.component.AppComponent;
import com.memoryleak.instantcare.dagger.component.DaggerAppComponent;
import com.memoryleak.instantcare.dagger.module.AppModule;

public class App extends Application{
    private AppComponent appComponent;
    public static final String CHANNEL_ID = "com.memoryleak.instantcare";

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        createNotificationChannel();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Instant care channel"
                    , NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
