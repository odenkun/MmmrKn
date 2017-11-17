package com.example.android.mmmrkn.api;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    MainComponent plus(MainModule m);
}
