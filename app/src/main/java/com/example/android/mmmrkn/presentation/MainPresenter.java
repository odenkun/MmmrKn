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
    //Contractインタフェースは、このクラスの下で宣言している
    private Contract contract;

    //ログイン通信に利用するインタフェース
    //Retrofitでは、APIに合わせてインタフェースを宣言し、
    //retrofit.create(LoginService.class)で、Retrofitがそのインタフェースを実装したクラスを生成する
    //ユーザはその自動生成されたクラスを利用する
    private LoginService loginService;

    //Daggerによってコンストラクタが呼び出される
    //コンストラクタインジェクションと呼ばれる方法
    @Inject
    public MainPresenter ( Contract contract, LoginService loginService ) {
        this.contract = contract;
        this.loginService = loginService;
    }

    //通信のリスト
    private CompositeDisposable disposables = new CompositeDisposable ();


    void login () {
        disposables.add(loginService.postTeacherLogin ( "guriko", "guriko" )
                //Observableが流すデータ(イベント)を作る処理、つまり通信は入出力用のスレッドで行う
                //詳しくは https://tech.mokelab.com/android/libs/RxJava/thread.html
                .subscribeOn ( Schedulers.io () )

                //結果をUIに反映したいので、結果をUIスレッド(メインスレッド)で受け取るよう指定
                //このメインスレッドを取得する処理はRxJavaではなく、RxAndroid
                .observeOn ( AndroidSchedulers.mainThread () )

                //以下、通信が終わった時に行う処理
                //LoginServiceでの返り値がCompletableなので引数は無し
                //詳しくは https://qiita.com/takahirom/items/f3e576e91b219c7239e7
                .subscribe ( () -> { //成功時
                    Log.e ( "login:", "login dekiatayo" );
                    //画面に反映
                    contract.onLogin ( true );
                }, e -> { //エラー時
                    Log.e ( "login:", e.getMessage () );
                    //画面に反映
                    contract.onLogin ( false );
                } ));
    }

    //全ての通信の結果通知を受け取らないようにする
    void dispose () {
        disposables.dispose ();
    }

    //MVPにおけるビューとプレゼンターの結合度を低くするためのインタフェース
    //ビューであるMainActivityが実装する
    public interface Contract {
        void onLogin ( boolean result );
    }
}