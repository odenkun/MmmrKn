package com.example.android.mmmrkn.presentation.login;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.di.ApplicationComponent;
import com.example.android.mmmrkn.di.login.LoginComponent;
import com.example.android.mmmrkn.di.login.LoginModule;
import com.example.android.mmmrkn.presentation.App;
import com.example.android.mmmrkn.presentation.attendances.AttendancesActivity;
import com.example.android.mmmrkn.presentation.login.LoginPresenter;
import com.example.android.mmmrkn.presentation.mode_select.ModeActivity;
import com.example.android.mmmrkn.presentation.teacher.SelectTeacherActivity;
import com.github.sjnyag.AnimationWrapLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import timber.log.Timber;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.Contract,ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int REQUEST_CODE = 3498;
    private static final String[] PERMISSIONS = new String[] { Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA };

    @Inject
    LoginPresenter presenter;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {

        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );

        TextView loginLink = findViewById(R.id.textvew_login_link);

        setSpannableString(loginLink, getString ( R.string.forgotPass ),"https://www.google.co.jp/");

        //全ての親である、アプリケーションコンポーネントを持ってくる
        ApplicationComponent appComponent = ( (App) getApplication () ).getComponent ();

        //親コンポーネントから、このアクティビティ専用のコンポーネントを取得する
        LoginComponent loginComponent = appComponent.plus ( new LoginModule ( this ) );

        //このクラスのメンバ変数に、適切なインスタンスを代入させる
        //今回はMainPresenterが自動で生成され、それがpresenterに代入される
        //Activityはコンストラクタをオーバーライドできないので、MainPresenterとは方法が異なる
        loginComponent.inject ( this );

        //ボタンが押されたらログインを試みる
        findViewById ( R.id.login_button ).setOnClickListener ( view -> attemptLogin () );

        boolean allGranted = true;
        for ( String permission : PERMISSIONS ) {
            if ( ContextCompat.checkSelfPermission (
                    this, permission ) != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }
        if ( ! allGranted ) {
            ActivityCompat.requestPermissions ( this, PERMISSIONS , REQUEST_CODE );
        }

        findViewById(R.id.button_account).setOnClickListener(view -> {
            Uri uri = Uri.parse("https://www.google.co.jp/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }
    private Button addButton(String text) {
        Button button = new Button ( this );
        button.setText(text);
        button.setTextSize ( 25 );
        button.setTextColor ( Color.WHITE );
        button.setBackgroundResource ( R.drawable.button_sub_layout );
        return button;
    }
    @Override
    public void onRequestPermissionsResult (
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {

        if ( requestCode != REQUEST_CODE ) return;

        for ( int grantResult : grantResults ) {
            if ( grantResult != PackageManager.PERMISSION_GRANTED ) {
                //許可されていないものがあるとき
                Toast.makeText ( this, "権限を許可してください。", Toast.LENGTH_SHORT ).show ();
                finish ();
            }
        }
    }
    void attemptLogin () {
        EditText idView = findViewById ( R.id.school_id );
        String id = idView.getText ().toString ();

        EditText passwordView = findViewById ( R.id.password );
        String password = passwordView.getText ().toString ();

        // 表示しているエラーをリセットする
        idView.setError ( null );
        passwordView.setError ( null );

        // nullでない or 長さが0 or 英数字以外の文字がある
        if ( TextUtils.isEmpty ( id ) || !isIdValid ( id ) ) {
            //エラーを表示
            idView.setError ( getString ( R.string.error_invalid_user_id ) );
            //入力カーソルを移動
            idView.requestFocus ();
        } else if ( TextUtils.isEmpty ( password ) || !isPasswordValid ( password ) ) {
            passwordView.setError ( getString ( R.string.error_invalid_password ) );
            passwordView.requestFocus ();
        } else {
            presenter.attemptLogin ( id, password );
        }
    }

    private boolean isIdValid ( String id ) {
        //英数字で構成されることのチェック
        return id.matches ( "[0-9a-zA-Z]+" );
    }

    private boolean isPasswordValid ( String password ) {
        //英数字で構成されることのチェック
        return password.matches ( "[0-9a-zA-Z]+" );
    }

    @Override
    public void onAuthStart () {
        showProgress ( true );
    }

    @Override
    public void onAuthFinish ( boolean result ) {

        showProgress ( result );
        Timber.d("result is%s", result);
        if (result) {
            Intent intent = new Intent ( this, SelectTeacherActivity.class );
            startActivity(intent);
            finish ();
        }else{
            // TODO: トーストからダイアログに変更する
            Toast.makeText (this,getString ( R.string.error_incorrect ),Toast.LENGTH_LONG).show ();
        }
    }

    @TargetApi (Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress ( final boolean show ) {

        //フォーム全体
        ScrollView loginFormView = findViewById ( R.id.login_form );
        //ログイン通信の進捗を示すビュー
        ProgressBar progressBar = findViewById ( R.id.login_progress );

        //アニメーションする時間
        int shortAnimTime = 100;

        loginFormView.setVisibility ( show ? View.GONE : View.VISIBLE );

        ViewPropertyAnimator formAnimator = loginFormView.animate ();

        //アニメーション時間
        formAnimator.setDuration ( shortAnimTime )
                //透明度
                .alpha ( show ? 0 : 1 );

        //アニメーションが終了したときの動作
        formAnimator.setListener ( new AnimatorListenerAdapter () {
            @Override
            public void onAnimationEnd ( Animator animation ) {
                loginFormView.setVisibility ( show ? View.GONE : View.VISIBLE );
            }
        } );

        progressBar.setVisibility ( show ? View.VISIBLE : View.GONE );
        ViewPropertyAnimator progressAnimator = progressBar.animate();

        progressAnimator.setDuration ( shortAnimTime )
                .alpha ( show ? 1 : 0 );

        progressAnimator.setListener ( new AnimatorListenerAdapter () {
            @Override
            public void onAnimationEnd ( Animator animation ) {
                progressBar.setVisibility ( show ? View.VISIBLE : View.GONE );
            }
        } );

    }

    @Override
    protected void onDestroy () {
        //通信の結果を受け取らなくする
        presenter.dispose ();
        super.onDestroy ();
    }
    //ここからURL_Linkの動き
    private void setSpannableString(View view,String message,String uriStr) {

        int start = 0;
        int end = message.length ();

        SpannableString ss = new SpannableString(message);
        // SpannableString にクリックイベント、パラメータをセットする
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Uri uri = Uri.parse(uriStr);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        // SpannableString をセットし、リンクを有効化する
        TextView textView = view.findViewById(R.id.textvew_login_link);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

}