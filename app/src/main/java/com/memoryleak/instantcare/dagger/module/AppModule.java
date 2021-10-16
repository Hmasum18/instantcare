package com.memoryleak.instantcare.dagger.module;

import android.app.Application;
import android.content.Context;

import com.memoryleak.instantcare.dagger.anotation.AppScope;
import com.memoryleak.instantcare.view.App;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @AppScope
    @Provides
    App provideApp() {
        return app;
    }

    @AppScope
    @Provides
    Application provideApplication() {
        return app;
    }

    @AppScope
    @Provides
    Context provideContext() {
        return app.getApplicationContext();
    }
}
