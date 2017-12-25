package com.example.android.mmmrkn.presentation.attendances;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.mmmrkn.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;



/**
 * 登園の登録をする
 */
public class AttendancesActivity extends AppCompatActivity implements QRFragment.QRFragmentListener {


    public static final String TAG = "AttendancesActivity";

    @Override
    protected void onCreate ( @Nullable Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView(R.layout.activity_attendances);
    }

    @Override
    public void onScanQR ( String studentId ) {
        AttendFragment attendFragment = (AttendFragment) getSupportFragmentManager ().findFragmentById ( R.id.attend_fragment );
        attendFragment.onScanQR ( studentId );
    }
}