package com.memoryleak.instantcare.view;

import android.app.Application;

import com.memoryleak.instantcare.dagger.component.AppComponent;
import com.memoryleak.instantcare.dagger.component.DaggerAppComponent;
import com.memoryleak.instantcare.dagger.module.AppModule;

public class App extends Application{
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
