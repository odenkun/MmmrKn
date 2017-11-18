package com.example.android.mmmrkn.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.di.ApplicationComponent;
import com.example.android.mmmrkn.di.MainComponent;
import com.example.android.mmmrkn.di.MainModule;

import javax.inject.Inject;

//MVP設計におけるViewクラス
//見た目に関わる処理を担当する
public class MainActivity extends AppCompatActivity implements MainPresenter.Contract{

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //全ての親である、アプリケーションコンポーネントを持ってくる
        ApplicationComponent appComponent = ((App)getApplication()).getComponent();

        //親コンポーネントから、このアクティビティ専用のコンポーネントを取得する
        MainComponent mainComponent = appComponent.plus(new MainModule(this));

        //このクラスのメンバ変数に、適切なインスタンスを代入させる
        //今回はMainPresenterが自動で生成され、それがpresenterに代入される
        //Activityはコンストラクタをオーバーライドできないので、MainPresenterとは方法が異なる
        mainComponent.inject(this);

        findViewById(R.id.thisistext).setOnClickListener( view -> {
            ((TextView)view).setText("これがラムダ式");
            presenter.login ();
        } );
    }

    @Override
    protected void onDestroy () {
        //通信の結果を受け取らなくする
        presenter.dispose ();
        super.onDestroy ();
    }

    //Contractインタフェースに定義されている
    @Override
    public void onLogin ( boolean result ) {

    }
}
