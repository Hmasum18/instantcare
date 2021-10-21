package com.memoryleak.instantcare.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.memoryleak.instantcare.BuildConfig;
import com.memoryleak.instantcare.R;
import com.memoryleak.instantcare.dagger.component.AppComponent;
import com.memoryleak.instantcare.dagger.component.MainActivityComponent;
import com.memoryleak.instantcare.dagger.module.MainActivityModule;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private MainActivityComponent mainActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getMainActivityComponent().inject(this);

       /* Intent intent = new Intent(this, PowerButtonService.class);
        intent.putExtra("POWER_BUTTON_CLICKED", 0);
        ContextCompat.startForegroundService(this, intent);*/
    }

    /**
     * creates {@link MainActivityComponent} if not created yet
     * @return the {@link MainActivityComponent} instance
     */
    public MainActivityComponent getMainActivityComponent(){
        if(mainActivityComponent == null){
            AppComponent appComponent = ((App) getApplication()).getAppComponent();
            mainActivityComponent = appComponent.activityComponentBuilder()
                    .activityModule(new MainActivityModule(this))
                    .build();
        }
        return mainActivityComponent;
    }
}