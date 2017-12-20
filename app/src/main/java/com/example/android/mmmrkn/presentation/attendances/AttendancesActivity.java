package com.example.android.mmmrkn.presentation.attendances;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.di.attendances.AttendancesModule;
import com.example.android.mmmrkn.infra.entity.Student;
import com.example.android.mmmrkn.presentation.App;
import com.example.android.mmmrkn.presentation.mode_select.ModeActivity;

import javax.inject.Inject;

public class AttendancesActivity extends AppCompatActivity implements AttendancesPresenter.Contract {
    @Inject
    AttendancesPresenter presenter;
    
    Intent intent;
    //組変更用
    TextView textparty;
    //園児名変更用
    TextView textname;
    //拒否ボタン
    Button btnDenial;
    //登園ボタン
    Button btnAttend;
    //モード選択ボタン
    Button btnMode;

    public static final String TAG = "fragment_dialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendances);
        btnDenial = findViewById(R.id.btn_denial);
        btnAttend = findViewById(R.id.btn_attend);
        textparty = findViewById(R.id.txt_class);
        textname = findViewById(R.id.txt_name);

        judgment();
        
        ((App) getApplication())
                .getComponent()
                .plus(new AttendancesModule(this))
                .inject(this);
        
        //拒否ボタン
        btnDenial.setOnClickListener(view -> reset());

        //登園ボタン
        btnAttend.setOnClickListener(view -> reset());

        //最下部に移動
        ScrollView scrollview = ((ScrollView) findViewById(R.id.scrollView));
        scrollview.post(new Runnable() {
            @Override
            public void run() {
                scrollview.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

    }

    /**
     * フラグメントダイアログを表示する。
     */
    final int TEST_DIALOG = 0;

    public void showFragmentDialog(int id) {
        switch (id) {
            case TEST_DIALOG:
                DialogFragment dialogFragment = TestAttendanceFragment.newInstance();
                dialogFragment.show(getSupportFragmentManager(), TAG);
        }
    }


    //空か判断してボタンの可視化又は不可視化する
    void judgment() {
        if (textname.getText() == "") {
            btnDenial.setVisibility(View.INVISIBLE);
            btnAttend.setVisibility(View.INVISIBLE);
            btnMode.setText("メニューへ戻る");
        } else {
            btnDenial.setVisibility(View.VISIBLE);
            btnAttend.setVisibility(View.VISIBLE);
        }
    }
    //空にする
    void reset(){
        textname.setText("");
        textparty.setText("");
        judgment();
    }
    
    //生徒データの挿入
    void insertProfile(String studentId){
        presenter.fetchProfile(studentId);
    }
    
    @Override
    public void onFetchComplete(Student sProfile) {
    textname.setText(sProfile.getName());
    textparty.setText(sProfile.getParty().getName());
    }
}