package com.example.android.mmmrkn.presentation.attendances.qr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mmmrkn.R;
import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.List;

import timber.log.Timber;

public class QRFragment extends Fragment {
    private Calendar lastAddedTime;

    private static final int SCAN_INTERVAL = 2;
    DecoratedBarcodeView barcodeView;

    public QRFragment () {
    }


    @Override
    public void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate ( R.layout.fragment_qr, container );
    }

    @Override
    public void onViewCreated ( View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated ( view, savedInstanceState );
        barcodeView = view.findViewById ( R.id.decoratedBarcodeView );
        barcodeView.decodeContinuous ( new BarcodeCallback () {
            @Override
            public void barcodeResult ( BarcodeResult result ) {
                Timber.d ( result.toString () );
                Calendar now = Calendar.getInstance ();
                if ( lastAddedTime != null ) {
                    //破壊的メソッドなのでclone
                    Calendar added = (Calendar) lastAddedTime.clone ();
                    added.add ( Calendar.SECOND, SCAN_INTERVAL );
                    //現在の時間
                    if ( now.before ( added ) ) {
                        Timber.d("前回から2秒以内の読み込み");
                        return;
                    }
                }
                //最初のスキャンor前より2秒後
                lastAddedTime = now;

                EventBus.getDefault().post(new QREvent (result.toString ()));
            }

            @Override
            public void possibleResultPoints ( List <ResultPoint> resultPoints ) {

            }
        } );
    }

    @Override
    public void onResume () {
        super.onResume ();
        barcodeView.resume ();
    }

    public class QREvent {
        public final String studentId;

        public QREvent(String studentId) {
            this.studentId = studentId;
        }
    }
}