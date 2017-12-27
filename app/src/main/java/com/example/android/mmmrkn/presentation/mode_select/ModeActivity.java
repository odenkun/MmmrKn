package com.example.android.mmmrkn.presentation.mode_select;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.di.mode.ModeModule;
import com.example.android.mmmrkn.presentation.App;
import com.example.android.mmmrkn.presentation.gohome.GoHomeActivity;
import com.example.android.mmmrkn.presentation.login.LoginActivity;
import com.example.android.mmmrkn.presentation.attendances_list.AttendancesListActivity;
import com.example.android.mmmrkn.presentation.attendances.AttendancesActivity;
import com.example.android.mmmrkn.presentation.teacher.SelectTeacherActivity;

import javax.inject.Inject;

import timber.log.Timber;

public class ModeActivity extends AppCompatActivity implements ModePresenter.Contract{

    @Inject
    ModePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        ((App) getApplication())
                .getComponent()
                .plus(new ModeModule(this))
                .inject(this);

        //職員選択ボタンを押された時の処理
        findViewById(R.id.btn_selectTeacher).setOnClickListener(view ->{
            startActivity( new Intent(this,SelectTeacherActivity.class) );
        });
        //ログアウトボタンを押された時の処理
        findViewById(R.id.btn_logout).setOnClickListener(view -> attemptLogout());

        //登園ボタンを押された時の処理
        findViewById(R.id.btn_attendances).setOnClickListener(view -> startActivity( new Intent(this,AttendancesActivity.class) ) );

        //降園ボタンを押された時の処理
        findViewById(R.id.btn_goHome).setOnClickListener(view -> startActivity( new Intent(this,GoHomeActivity.class) ) );

        //登園リストボタンを押された時の処理
        findViewById(R.id.btn_attendancesList).setOnClickListener(view -> startActivity( new Intent(this,AttendancesListActivity.class) ) );
    }

    void attemptLogout () {
        presenter.attemptLogout();
    }

    @Override
    public void onLogoutFinish ( boolean result ) {
        if (result) {
            Intent intent = new Intent ( this, LoginActivity.class );
            startActivity(intent);
            finish ();
        }else{
            // TODO: トーストからダイアログに変更する
            Toast.makeText (this,getString ( R.string.failed_logout ),Toast.LENGTH_LONG).show ();
        }
    }
    @Override
    protected void onDestroy () {
        //通信の結果を受け取らなくする
        presenter.dispose ();
        super.onDestroy ();
    }
}