package com.example.android.mmmrkn.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.presentation.login.LoginActivity;
import com.example.android.mmmrkn.presentation.party.SelectPartyActivity;
import com.example.android.mmmrkn.presentation.teacher.SelectTeacherActivity;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import javax.inject.Inject;

import okhttp3.Cookie;

/**
 * セッション情報に合わせて起動画面を変更する
 */
public class StartActivity extends AppCompatActivity {

    //Cookieを保持する
    @Inject
    SharedPrefsCookiePersistor persistor;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );

         ( (App) getApplication () )
                 .getComponent ()
                 .plus()
                 .inject ( this );

        boolean hasToken = false;
        for ( Cookie cookie : persistor.loadAll () ) {
            //CookieのキーがsessionInfoであるものが存在する
            //かつ、期限切れでない
            if (cookie.name ().equals ( getString ( R.string.auth_token_name ) )
                    && System.currentTimeMillis() < cookie.expiresAt ()   ) {
                hasToken = true;
            }
        }

        Intent activityIntent;
        //認証済みの場合は保育士の選択画面に遷移
        if ( hasToken ) {
            activityIntent = new Intent(this,SelectPartyActivity.class);
        } else {
            //認証できていないのでログイン画面に遷移
            activityIntent = new Intent(this, LoginActivity.class);
        }

        //遷移を開始
        startActivity(activityIntent);
        //このアクティビティを終了する
        finish();
    }
}
