package com.example.android.mmmrkn.di;

import com.example.android.mmmrkn.presentation.MainActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {MainModule.class})
public interface MainComponent{
    void inject(MainActivity m);
}