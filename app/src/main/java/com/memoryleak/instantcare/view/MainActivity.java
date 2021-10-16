package com.memoryleak.instantcare.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.memoryleak.instantcare.R;
import com.memoryleak.instantcare.dagger.component.AppComponent;
import com.memoryleak.instantcare.dagger.component.MainActivityComponent;
import com.memoryleak.instantcare.dagger.module.MainActivityModule;

public class MainActivity extends AppCompatActivity {

    private MainActivityComponent mainActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDagger().inject(this);
    }

    /**
     * creates {@link MainActivityComponent} if not created yet
     * @return the {@link MainActivityComponent} instance
     */
    public MainActivityComponent initDagger(){
        if(mainActivityComponent == null){
            AppComponent appComponent = ((App) getApplication()).getAppComponent();
            mainActivityComponent = appComponent.activityComponentBuilder()
                    .activityModule(new MainActivityModule(this))
                    .build();
        }
        return mainActivityComponent;
    }
}