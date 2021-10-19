package com.memoryleak.instantcare.dagger.component;

import com.memoryleak.instantcare.dagger.anotation.MainActivityScope;
import com.memoryleak.instantcare.dagger.module.MainActivityModule;
import com.memoryleak.instantcare.view.MainActivity;
import com.memoryleak.instantcare.view.fragment.HomeFragment;
import com.memoryleak.instantcare.view.fragment.SignInFragment;

import dagger.Subcomponent;

@MainActivityScope
@Subcomponent(modules = {MainActivityModule.class})
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);

    void inject(SignInFragment signInFragment);

    void inject(HomeFragment homeFragment);

    @Subcomponent.Builder
    interface Builder{
        MainActivityComponent build();
        Builder activityModule(MainActivityModule mainActivityModule);
    }
}
