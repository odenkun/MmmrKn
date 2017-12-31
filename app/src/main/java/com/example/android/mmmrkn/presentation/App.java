package com.example.android.mmmrkn.presentation;

import android.app.Application;

import com.example.android.mmmrkn.di.ApplicationComponent;
import com.example.android.mmmrkn.di.ApplicationModule;
import com.example.android.mmmrkn.di.DaggerApplicationComponent;
import com.example.android.mmmrkn.infra.entity.Teacher;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

//アプリの起動中ずっと生存するクラス
//Applicationクラスを継承して作るので、カスタムアプリケーションクラスと呼ばれる
public class App extends Application {
    ApplicationComponent applicationComponent;
    Teacher teacher;

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

    @Override
    public void onCreate () {
        super.onCreate ();
        Timber.plant( new Timber.DebugTree());
        Picasso.with ( this ).setLoggingEnabled ( true );

    }

    public Teacher getTeacher () {
        return teacher;
    }

    public void setTeacher ( Teacher teacher ) {
        if (teacher == null) {
            throw new NullPointerException ( "selectedTeacher is null" );
        }
        this.teacher = teacher;
    }
}