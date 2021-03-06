package com.example.android.mmmrkn.presentation.attendances;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.databinding.ActivityAttendancesBinding;
import com.example.android.mmmrkn.infra.entity.AttendancesStudent;
import com.example.android.mmmrkn.presentation.mode_select.ModeActivity;

public class AttendancesActivity extends AppCompatActivity {
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
        //ActivityAttendancesBinding ats = DataBindingUtil.setContentView(this, R.layout.activity_attendances);
        //AttendancesStudent attendancesStudent = new AttendancesStudent("");
        btnDenial = findViewById(R.id.btn_denial);
        btnAttend = findViewById(R.id.btn_attend);
        btnMode = findViewById(R.id.btn_mode);
        textparty = findViewById(R.id.txt_class);
        textname = findViewById(R.id.txt_name);


        judgment();

        //拒否ボタン
        btnDenial.setOnClickListener(view -> reset());

        //登園ボタン
        btnAttend.setOnClickListener(view -> reset());

        btnMode.setOnClickListener(view -> {
            if (textname.getText() == "") {
                //モード選択画面へ
                startActivity(new Intent(this, ModeActivity.class));
                finish();
            }
            else{
                reset();
            }
        });

        //本来は音声認識
        //ボタンの作成、押下した動き
        btnMode.setOnClickListener(view -> {
            showFragmentDialog(TEST_DIALOG);
            judgment();
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
}