package com.example.android.mmmrkn.presentation.attendances.attend;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.databinding.FragmentAttendBinding;
import com.example.android.mmmrkn.di.attendances.AttendancesModule;
import com.example.android.mmmrkn.infra.entity.Attendance;
import com.example.android.mmmrkn.infra.entity.Student;
import com.example.android.mmmrkn.infra.entity.Teacher;
import com.example.android.mmmrkn.presentation.App;
import com.example.android.mmmrkn.presentation.attendances.qr.QRFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;


public class AttendFragment extends Fragment implements AttendFragmentPresenter.Contract {
    @Inject
    AttendFragmentPresenter presenter;
    ViewModel viewModel = new ViewModel ();
    Teacher teacher;

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
        FragmentAttendBinding binding = DataBindingUtil.inflate ( inflater, R.layout.fragment_attend, container, true );
        binding.setViewModel ( viewModel );
        Student profile = new Student ();
        profile.setName ( "aaaaaaa" );
        profile.setPicturePath ( "c_kuroki.jpg" );

        viewModel.setStudent ( profile );
        binding.btnAttend.setOnClickListener ( view -> onClick ( true ) );
        binding.btnDenial.setOnClickListener ( view -> onClick ( false ) );
        return binding.getRoot ();
    }


    @Override
    public void onAttach ( Context context ) {
        super.onAttach ( context );
        App app = (App) getActivity ().getApplication ();
        app.getComponent ()
                .plus ( new AttendancesModule ( this ) )
                .inject ( this );
        teacher = app.getTeacher ();
    }

    @Override
    public void onStart () {
        super.onStart ();
        EventBus.getDefault ().register ( this );
        presenter.onStart ();

    }

    @Override
    public void onAttendRegistered ( boolean result ) {
        if (result) {
            Toast.makeText ( this.getContext (), "登園を記録できました。", Toast.LENGTH_SHORT ).show ();
        }else{
            Toast.makeText ( this.getContext (), "登園の記録に失敗しました。", Toast.LENGTH_SHORT ).show ();
        }
    }

    @Subscribe
    public void onScanQR ( QRFragment.QREvent qrEvent ) {
        // TODO: 2017/12/26 くるくるの表示 
        presenter.fetchStudent ( qrEvent.studentId );
    }

    public void onClick ( boolean isAttend ) {

        Attendance.Condition condition;
        // TODO: 2017/12/27 くるくるの表示
        if (isAttend) {
            if (viewModel.getCondition ()) {
                condition = Attendance.Condition.GOOD;
            }else{
                condition = Attendance.Condition.SUBTLE;
            }
        }else{
            condition = Attendance.Condition.BAD;
        }
        viewModel.getStudent ().getAttendance ().setCondition ( condition.toString () );
        presenter.registerAttend ( viewModel.getStudent (), teacher.getTeacherId () );
    }

    @Override
    public void onStop () {
        EventBus.getDefault ().unregister ( this );
        presenter.onStop ();
        super.onStop ();
    }

    @Override
    public void onDetach () {
        super.onDetach ();
        presenter.dispose ();
    }

    @Override
    public void onConnectionFailed () {
        Toast.makeText ( getActivity (), "接続に失敗しました。", Toast.LENGTH_SHORT ).show ();
        getActivity ().finish ();
    }

    @Subscribe
    public void onBTStateChanged ( BTListener.BTEvent btEvent ) {
        if ( btEvent.isDisconnect () ) {
            Toast.makeText ( getActivity (), "マイクが取り外されました。", Toast.LENGTH_SHORT ).show ();
            getActivity ().finish ();
        }
    }

    /**
     * QRコードを読み込んだとき
     * @param students 学生のリスト
     */
    @Override
    public void onNameRecognized ( List <Student> students ) {
        if ( students == null || students.size () < 1 ) {
            throw new RuntimeException ( "リストが不正" );
        }
        //音声認識を実装したら
        if ( students.size () == 1 ) {
            viewModel.setStudent ( students.get ( 0 ) );
        }
    }


}