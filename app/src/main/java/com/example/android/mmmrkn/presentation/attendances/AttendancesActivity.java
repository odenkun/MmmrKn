package com.example.android.mmmrkn.presentation.attendances;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.databinding.ActivityAttendancesBinding;
import com.example.android.mmmrkn.di.attendances.AttendancesModule;
import com.example.android.mmmrkn.infra.entity.StudentProfile;
import com.example.android.mmmrkn.presentation.App;
import com.example.android.mmmrkn.presentation.attendances.dialog.TestAttendanceFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;


/**
 * 登園の登録をする
 */
public class AttendancesActivity extends AppCompatActivity
        implements AttendancesPresenter.Contract,
        ActivityCompat.OnRequestPermissionsResultCallback {
    @Inject
    AttendancesPresenter presenter;

    ActivityAttendancesBinding binding;

    public static final String TAG = "AttendancesActivity";
    private static final int REQUEST_CODE = 3498;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_attendances );
        binding = DataBindingUtil.setContentView (this, R.layout.activity_attendances );
        ( (App) getApplication () )
                .getComponent ()
                .plus ( new AttendancesModule ( this ) )
                .inject ( this );

    }

    @Override
    protected void onStart () {
        super.onStart ();
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions ( this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE );
        }else{
            presenter.readyMic (this);
        }
    }

    @Override
    public void onNameRecognized ( List<StudentProfile> profiles ) {
        if (profiles == null || profiles.isEmpty ()) {
            throw new RuntimeException ( "bad student list" );
        }else if (profiles.size () == 1 ) {
            binding.setStudent ( profiles.get ( 0 ) );
        } else if ( profiles.size () > 1 ) {
            //生徒を選択させるダイアログを表示
            DialogFragment dialogFragment = TestAttendanceFragment.newInstance (profiles);
            dialogFragment.show ( getSupportFragmentManager (), TAG );
        }
    }

    @Override
    public void onRequestPermissionsResult (
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {

        if ( requestCode != REQUEST_CODE ) return;

        for ( int grantResult : grantResults ) {
            if ( grantResult != PackageManager.PERMISSION_GRANTED ) {
                //許可されていないものがあるとき
                Toast.makeText ( this, "権限を許可してください。", Toast.LENGTH_SHORT ).show ();
                return;
            }
        }
        //すべて許可されていたなら
        presenter.readyMic (this);
    }

    @Override
    public void onConnectionFailed () {
        Toast.makeText ( this, "接続に失敗しました。", Toast.LENGTH_SHORT ).show ();
        finish ();
    }

    @Override
    public void onBTDeviceDisconnected () {
        Toast.makeText ( this, "マイクが取り外されました。", Toast.LENGTH_SHORT ).show ();
        finish ();
    }

    @Override
    protected void onDestroy () {
        super.onDestroy ();
        this.binding = null;
        presenter.dispose ();
    }
}