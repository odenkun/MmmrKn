package com.example.android.mmmrkn.presentation.attendances;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.presentation.mode_select.ModeActivity;

public class AttendancesActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendances);

        Button btnDenial=findViewById(R.id.btn_denial);
        Button btnAttend=findViewById(R.id.btn_attend);
        Button btnMode=findViewById(R.id.btn_mode);
        TextView textView=findViewById(R.id.txt_class);
        if(textView.getText()=="") {
            btnDenial.setVisibility(View.INVISIBLE);
            btnAttend.setVisibility(View.INVISIBLE);
            btnMode.setText("メニューへ戻る");
            btnMode.setOnClickListener(view -> {
                //モード選択画面へ
                intent = new Intent(this, ModeActivity.class);
                startActivity(intent);
                finish();
            });
        }
        //登園ボタン
        btnAttend.setOnClickListener(view ->{
            MoveAtt();
        });
        //戻るボタン
        btnMode.setOnClickListener(view ->{
            MoveAtt();
        });
        //拒否ボタン
        btnDenial.setOnClickListener(view ->{
            MoveAtt();
        });
    }
    void MoveAtt() {
        intent=new Intent(this,AttendancesActivity.class);
        startActivity(intent);
        finish();
    }

}
