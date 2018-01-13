package com.example.android.mmmrkn.presentation.mode_select;

import android.support.v7.app.AppCompatActivity;

import com.example.android.mmmrkn.infra.api.LogoutService;
import com.example.android.mmmrkn.presentation.Presenter;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class ModePresenter extends Presenter {
    private Contract contract;

    private final LogoutService logoutService;

    //cookieの削除をする？
    private final SharedPrefsCookiePersistor persistor;

    @Inject
    public ModePresenter ( Contract contract, LogoutService logoutService, SharedPrefsCookiePersistor persistor ) {
        this.contract = contract;
        this.logoutService = logoutService;
        this.persistor = persistor;
    }

    void attemptLogout () {
        //まだ終わっていないログアウト通信があるとき中断
        if ( this.hasDisposables () ) {
            return;
        }
        //チェックが通ったので通信する
        disposables.add ( logoutService.postSchoolLogout ()
                .subscribeOn ( Schedulers.io () )
                .observeOn ( AndroidSchedulers.mainThread () )
                .subscribe ( () -> {
                    Timber.d ( "logout successful" );
                    persistor.clear ();
                    contract.onLogoutFinish ( true );
                }, e -> {
                    Timber.e ( e );
                    contract.onLogoutFinish ( false );
                } ) );

    }

    //参照の切断
    @Override
    public void dispose () {
        super.dispose ();
        this.contract = null;
    }

    public interface Contract {
        /**
         *
         * @param result ログアウトが成功か失敗か
         */
        void onLogoutFinish ( boolean result );
    }
}
