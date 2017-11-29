package com.example.android.mmmrkn.presentation.drop_off;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.presentation.cardview_gohome_dialog.GoHomeDialogActivity;
import com.example.android.mmmrkn.presentation.mode_select.ModeActivity;

public class DropOffActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendances);

        findViewById(R.id.btn_mode).setOnClickListener(view-> {
            //モード選択画面へ
            intent = new Intent(this, ModeActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.btn_attend).setOnClickListener(view ->{
            intent=new Intent(this,GoHomeDialogActivity.class);
            startActivity(intent);
            finish();
        });

    }
}
