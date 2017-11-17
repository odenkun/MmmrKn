package com.example.android.mmmrkn.presentation;

import android.app.Application;

import com.example.android.mmmrkn.api.ApplicationComponent;
import com.example.android.mmmrkn.api.ApplicationModule;
import com.example.android.mmmrkn.api.DaggerApplicationComponent;

public class App extends Application {
    public ApplicationComponent component() {
        return DaggerApplicationComponent.builder()
                       .applicationModule(new ApplicationModule(this))
                       .build();
    }
}
