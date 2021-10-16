package com.memoryleak.instantcare.dagger.component;

import com.memoryleak.instantcare.dagger.anotation.AppScope;
import com.memoryleak.instantcare.dagger.module.AppModule;
import com.memoryleak.instantcare.dagger.module.NetworkModule;

import dagger.Component;

@AppScope
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent{
    MainActivityComponent.Builder activityComponentBuilder();
}
