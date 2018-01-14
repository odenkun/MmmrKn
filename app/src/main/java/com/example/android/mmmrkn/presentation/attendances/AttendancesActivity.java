package com.example.android.mmmrkn.presentation.attendances;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.mmmrkn.R;


/**
 * 登園の登録をする
 */
public class AttendancesActivity extends AppCompatActivity {


    public static final String TAG = "AttendancesActivity";

    @Override
    protected void onCreate ( @Nullable Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView(R.layout.activity_attendance);
    }

}