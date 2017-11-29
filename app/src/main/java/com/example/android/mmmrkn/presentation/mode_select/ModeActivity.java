package com.example.android.mmmrkn.presentation.mode_select;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.presentation.cardview_gohome_dialog.GoHomeDialogActivity;
import com.example.android.mmmrkn.presentation.party_search.PartySearchActivity;
import com.example.android.mmmrkn.presentation.drop_off.DropOffActivity;
import com.example.android.mmmrkn.presentation.teacher.SelectTeacherActivity;

public class ModeActivity extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);


        //職員選択ボタンを押された時の処理
        findViewById(R.id.logout).setOnClickListener(view ->{
                intent=new Intent(this,SelectTeacherActivity.class);
            startActivity(intent);
    });


        //登園ボタンを押された時の処理
        findViewById(R.id.dropp_off).setOnClickListener(view -> {
            intent = new Intent(this, DropOffActivity.class);
            startActivity(intent);
        });

        //降園ボタンを押された時の処理(未実装)
        findViewById(R.id.get_off).setOnClickListener(view -> {
            intent = new Intent(this, GoHomeDialogActivity.class);
            startActivity(intent);
        });

        //登園リストボタンを押された時の処理(未実装)
        findViewById(R.id.dropp_off_list).setOnClickListener(view -> {
            intent = new Intent(this, PartySearchActivity.class);
            startActivity(intent);
        });
    }
}