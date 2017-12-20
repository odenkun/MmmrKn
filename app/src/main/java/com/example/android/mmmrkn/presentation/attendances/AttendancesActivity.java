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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //このイベントはzxing以外も実行するため
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                AttendFragment attendFragment = (AttendFragment) getSupportFragmentManager ().findFragmentById ( R.id.attend_fragment );
                attendFragment.onScanQR ( result.getContents() );
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onScanQR ( String studentId ) {

    }
}