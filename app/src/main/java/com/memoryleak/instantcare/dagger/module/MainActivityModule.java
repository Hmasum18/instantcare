package com.memoryleak.instantcare.dagger.module;

import androidx.activity.result.ActivityResultCaller;

import com.memoryleak.instantcare.dagger.anotation.MainActivityScope;
import com.memoryleak.instantcare.view.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule{
    private final MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @MainActivityScope
    @Provides
    ActivityResultCaller provideActivityResultCaller(){return  mainActivity;}
}
