package com.example.android.mmmrkn.presentation.attendances.qr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
                EventBus.getDefault().post(new QREvent (result.toString ()));
            }

            @Override
            public void possibleResultPoints ( List <ResultPoint> resultPoints ) {

            }
        } );
    }

    public class TestQRTask extends AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground ( String... strings ) {
            try {
                Thread.sleep ( 3000 );
                EventBus.getDefault().post(new QREvent (strings[0]));
            }catch ( Exception e ) {

            }
            return null;
        }
    }

    @Override
    public void onResume () {
        super.onResume ();
        barcodeView.resume ();
        new TestQRTask ().execute ( "bc332213-fcea-4ae5-97fe-4bb4f911c1d0" );

    }
    @Override
    public void onPause () {
        super.onPause();
        barcodeView.pause ();
    }

    public class QREvent {
        public final String studentId;

        public QREvent(String studentId) {
            this.studentId = studentId;
        }
    }
}
