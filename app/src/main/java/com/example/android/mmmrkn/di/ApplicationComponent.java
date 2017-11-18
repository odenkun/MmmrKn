package com.example.android.mmmrkn.di;

import javax.inject.Singleton;

import dagger.Component;

//
@Singleton
//ComponentはSubomponentの親
//modules={}の中には使用するモジュールの一覧を書く
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    MainComponent plus(MainModule m);
}
