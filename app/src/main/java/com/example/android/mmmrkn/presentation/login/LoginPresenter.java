package com.example.android.mmmrkn.presentation.login;


import com.example.android.mmmrkn.infra.api.LoginService;
import com.example.android.mmmrkn.presentation.Presenter;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cookie;
import timber.log.Timber;


/**
 * MVP設計におけるプレゼンター
 * 見た目とデータの入出力以外の処理(ビジネスロジック)を担当する
 */
public class LoginPresenter extends Presenter {

    //インタフェースであるContractを実装したMainActivityが代入される
    //MainActivity型でなくContract型である理由は、ビューとプレゼンター間の依存性を低くするため
    //Contractインタフェースは、このクラスの下で宣言している
    private Contract contract;

    //ログイン通信に利用するインタフェース
    //Retrofitでは、APIに合わせてインタフェースを宣言し、
    //retrofit.create(LoginService.class)で、Retrofitがそのインタフェースを実装したクラスを生成する
    //ユーザはその自動生成されたクラスを利用する
    private final LoginService loginService;


    //Cookieを保持する
    private final SharedPrefsCookiePersistor persistor;

    /**
     *
     * Daggerによってコンストラクタが呼び出される
     * コンストラクタインジェクションと呼ばれる方法
     *
     * @param contract LoginActivityが入る
     * @param loginService ログイン通信に利用するRetrofitのインスタンス
     * @param persistor クッキーをSharedPreferencesに入れてくれるやつ
     */
    @Inject
    public LoginPresenter ( Contract contract, LoginService loginService, SharedPrefsCookiePersistor persistor ) {
        this.contract = contract;
        this.loginService = loginService;
        this.persistor = persistor;
    }

    /***
     * IDとパスワードのバリデーションが終わった後、
     * ログイン通信を行うためLoginActivityから呼び出される
     *
     * @param id ログインに利用する保育園のID
     * @param pass ログインに利用する保育園のパスワード
     */

    void attemptLogin ( String id, String pass ) {
        //まだ終わっていないログイン通信があるとき中断
       if (this.hasDisposables ()) {
           return;
       }

        //チェックが通ったので通信する

        //あとで破棄できるように参照を残すためリストに追加
        disposables.add ( loginService.postSchoolLogin ( id, pass )
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
                    disposables.clear ();
                    //画面に反映
                    contract.onAuthFinish ( true );
                }, e -> { //エラー時
                    Timber.e ( e );
                    disposables.clear ();
                    //画面に反映
                    contract.onAuthFinish ( false );
                } ) );
    }

    /**
     * クッキーを消去する
     */
    void clearCookies () {
        Timber.d ( "started" );
        for ( Cookie cookie : persistor.loadAll () ) {
            Timber.d ( cookie.toString () );
        }
        persistor.clear ();
    }

    @Override
    public void dispose() {
        super.dispose();
        this.contract = null;
    }

    /**
     * MVPにおけるビューとプレゼンターの結合度を低くするためのインタフェース
     * ビューであるMainActivityが実装する
     */
    public interface Contract {
        void onAuthStart();
        /**
         *
         * @param result ログインが成功か失敗か
         */
        void onAuthFinish ( boolean result );
    }
}