package com.example.android.mmmrkn.presentation.attendances;

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

import java.util.List;

import timber.log.Timber;

public class QRFragment extends Fragment {
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private String mParam1;
//    private String mParam2;

    private QRFragmentListener mListener;
    DecoratedBarcodeView barcodeView;
    public QRFragment () {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
//    public static QRFragment newInstance ( String param1, String param2 ) {
//        QRFragment fragment = new QRFragment ();
//        Bundle args = new Bundle ();
//        args.putString ( ARG_PARAM1, param1 );
//        args.putString ( ARG_PARAM2, param2 );
//        fragment.setArguments ( args );
//        return fragment;
//    }

    @Override
    public void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        if ( getArguments () != null ) {
//            mParam1 = getArguments ().getString ( ARG_PARAM1 );
//            mParam2 = getArguments ().getString ( ARG_PARAM2 );
        }

    }

    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate ( R.layout.fragment_qr, container);
    }

    @Override
    public void onAttach ( Context context ) {
        super.onAttach ( context );
        if ( context instanceof QRFragmentListener ) {
            mListener = (QRFragmentListener) context;
//            IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
//
//            integrator.initiateScan();
        } else {
            throw new RuntimeException ( context.toString ()
                    + " must implement OnFragmentInteractionListener" );
        }
    }

    @Override
    public void onViewCreated ( View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated ( view, savedInstanceState );
        barcodeView = view.findViewById ( R.id.decoratedBarcodeView );
        barcodeView.decodeContinuous ( new BarcodeCallback () {
            @Override
            public void barcodeResult ( BarcodeResult result ) {
                Timber.d(result.toString ());
            }

            @Override
            public void possibleResultPoints ( List<ResultPoint> resultPoints ) {

            }
        } );
    }

    @Override
    public void onResume () {
        super.onResume ();
        barcodeView.resume ();
    }

    @Override
    public void onDetach () {
        super.onDetach ();
        mListener = null;
    }

    /**
     * アクティビティが実装する
     */
    public interface QRFragmentListener {
        void onScanQR ( String studentId );
    }
}
