package com.example.android.mmmrkn.presentation;

import android.util.Log;
import com.example.android.mmmrkn.infra.api.LoginService;
import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

//MVP設計におけるプレゼンター
//見た目とデータの入出力以外の処理(ビジネスロジック)を担当する
public class MainPresenter {

    //インタフェースであるContractを実装したMainActivityが代入される
    //MainActivity型でなくContract型である理由は、ビューとプレゼンター間の依存性を低くするため
    private Contract contract;

    //ログイン通信に利用するインタフェース
    //Retrofitでは、APIに合わせてインタフェースを宣言し、
    //retrofit.create(LoginService.class)で、Retrofitがそのインタフェースを実装したクラスを生成する
    //開発者はその自動生成されたクラスを利用する
    private LoginService loginService;

    //このアノテーションは、Daggerによって、変数の型に合ったインスタンスが、自動的に変数に代入されることを示す
    //Contractインタフェースは、このクラスの下で宣言している
    @Inject
    public MainPresenter ( Contract contract, LoginService loginService ) {
        this.contract = contract;
        this.loginService = loginService;
    }

    // TODO: 2017/11/17
    private CompositeDisposable disposables = new CompositeDisposable ();


    void login() {
        loginService.postTeacherLogin ("guriko","guriko")
                //通信は別スレッドで行う
                .subscribeOn( Schedulers.newThread())
                //結果をメインスレッドで受け取る
                .observeOn( AndroidSchedulers.mainThread())
                //以下、通信が終わった時に行う処理
                .subscribe(() -> { //成功時
                    Log.e("login:", "login dekiatayo");
                    contract.onLogin ( true );
                }, e -> { //エラー時
                    Log.e("login:", e.getMessage ());
                    contract.onLogin ( false );
                });
    }

    private void dispose () {
        disposables.dispose ();
    }

    public interface Contract {
        void onLogin ( boolean result );
    }
}