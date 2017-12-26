package com.example.android.mmmrkn.presentation.attendances.attend;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.mmmrkn.BR;
import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.databinding.FragmentAttendBinding;
import com.example.android.mmmrkn.di.attendances.AttendancesModule;
import com.example.android.mmmrkn.infra.entity.Student;
import com.example.android.mmmrkn.presentation.App;
import com.example.android.mmmrkn.presentation.attendances.qr.QRFragment;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;


public class AttendFragment extends Fragment implements AttendFragmentPresenter.Contract {
    @Inject
    AttendFragmentPresenter presenter;
    ViewModel studentHolder = new ViewModel ();

    public AttendFragment () {
        // Required empty public constructor
    }

    @Override
    public void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        FragmentAttendBinding binding = DataBindingUtil.inflate (inflater, R.layout.fragment_attend ,container,true);

        binding.setHolder ( studentHolder );
        Student profile = new Student ();
        profile.setName ( "aaaaaaa" );
        profile.setPicturePath ( "c_kuroki.jpg" );
        studentHolder.setStudent ( profile );
        return binding.getRoot ();
    }


    @Override
    public void onAttach ( Context context ) {
        super.onAttach ( context );
        ( (App) getActivity().getApplication () )
                .getComponent ()
                .plus ( new AttendancesModule ( this ) )
                .inject ( this );

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        presenter.onStart ();
    }

    @Subscribe
    public void onScanQR ( QRFragment.QREvent qrEvent) {
        // TODO: 2017/12/26 くるくるの表示 
        presenter.fetchStudent ( qrEvent.studentId );
    }
    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        presenter.onStop ();
        super.onStop();
    }
    @Override
    public void onDetach () {
        super.onDetach ();
        presenter.dispose ();
    }

    @Override
    public void onConnectionFailed () {
        Toast.makeText ( getActivity (), "接続に失敗しました。", Toast.LENGTH_SHORT ).show ();
        getActivity ().finish();
    }

    @Subscribe
    public void onBTStateChanged (BTListener.BTEvent btEvent) {
        if (btEvent.isDisconnect ()) {
            Toast.makeText ( getActivity (), "マイクが取り外されました。", Toast.LENGTH_SHORT ).show ();
            getActivity ().finish ();
        }
    }

    /**
     * QRコードを読み込んだとき
     * @param students 学生のリスト
     */
    @Override
    public void onNameRecognized ( List<Student> students ) {
        if (students == null || students.size () < 1) {
            throw new RuntimeException ( "リストが不正" );
        }
        //音声認識を実装したら
        if (students.size () == 1) {
            studentHolder.setStudent ( students.get(0) );
        }
    }
    //チンパンコード
    public static class ViewModel extends BaseObservable {
        private Student student;

        @Bindable
        public Student getStudent () {
            return student;
        }

        public void setStudent ( Student student ) {
            this.student = student;
            notifyPropertyChanged ( BR.student );

        }
        @BindingAdapter ( { "picturePath", "gender" })
        public static void loadImage ( ImageView view, String picturePath, String gender ) {

            int frameColor = R.color.manFrame;
            int placeHolderID = R.drawable.boy_happy;
            if ( gender != null && gender.equals ( "woman" ) ) {
                frameColor = R.color.womanFrame;
                placeHolderID = R.drawable.girl_happy;
            }
            Picasso.with ( view.getContext () ).setLoggingEnabled ( true );

            Picasso.with ( view.getContext () )
                    .load ( "https://mmmr-mock-api.mybluemix.net/images/students/" + picturePath )
                    .placeholder ( placeHolderID )
                    .fit ()
                    .transform ( new RoundedTransformationBuilder ()
                            .borderColor ( frameColor )
                            .borderWidthDp ( 6 )
                            .oval ( true )
                            .build () )
                    .into ( view );
        }
    }
}
