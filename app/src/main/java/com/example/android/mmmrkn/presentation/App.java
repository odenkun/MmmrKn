package com.example.android.mmmrkn.presentation;

import android.app.Application;

import com.example.android.mmmrkn.di.ApplicationComponent;
import com.example.android.mmmrkn.di.ApplicationModule;
import com.example.android.mmmrkn.di.DaggerApplicationComponent;

//アプリの起動中ずっと生存するクラス
//Applicationクラスを継承して作るので、カスタムアプリケーションクラスと呼ばれる
public class App extends Application {
    ApplicationComponent applicationComponent;

    public ApplicationComponent getComponent() {
        if (applicationComponent == null) {

            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return applicationComponent;
    }
}
