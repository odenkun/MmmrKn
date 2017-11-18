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

            //DaggerApplicationComponentは、このプロジェクトをビルドすると、
            //ApplicationComponentインタフェースをもとにDaggerが自動生成してくれる。
            applicationComponent = DaggerApplicationComponent.builder()

                    //なぜthisが必要かというと、ApplicationModule側で、
                    //Retrofitなどのインスタンスを生成するために、Contextが必要なため。
                    .applicationModule(new ApplicationModule(this))

                    //設定が終わったのでDaggerApplicationComponentインスタンスを生成する。
                    .build();
        }
        return applicationComponent;
    }
}
