package com.example.android.mmmrkn.presentation.attendances.attend;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.databinding.FragmentAttendBinding;
import com.example.android.mmmrkn.di.attendances.AttendancesModule;
import com.example.android.mmmrkn.infra.entity.Attendance;
import com.example.android.mmmrkn.infra.entity.Student;
import com.example.android.mmmrkn.infra.entity.Teacher;
import com.example.android.mmmrkn.infra.voice.Result;
import com.example.android.mmmrkn.infra.voice.VoiceTransmitter;
import com.example.android.mmmrkn.presentation.App;
import com.example.android.mmmrkn.presentation.qr.QRFragment;
import com.github.sjnyag.AnimationWrapLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;


public class AttendFragment extends Fragment implements AttendFragmentPresenter.Contract, Async.Contract {
    @Inject
    AttendFragmentPresenter presenter;
    final ViewModel viewModel = new ViewModel ();
    Teacher teacher;
    final LinkedHashSet<String> families = new LinkedHashSet <> (  );
    final LinkedHashSet<String> times = new LinkedHashSet <> (  );

    AnimationWrapLayout familyLayout;
    AnimationWrapLayout timeLayout;


    public AttendFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        FragmentAttendBinding binding = DataBindingUtil.inflate ( inflater, R.layout.fragment_attend, container, true );
        binding.setViewModel ( viewModel );
//        Student profile = new Student ();
//        profile.setName ( "aaaaaaa" );
//        profile.setPicturePath ( "c_kuroki.jpg" );
//
//        viewModel.setStudent ( profile );
        binding.btnAttend.setOnClickListener ( view -> onClick ( true ) );
        binding.btnDenial.setOnClickListener ( view -> onClick ( false ) );
        familyLayout =  binding.getRoot ().findViewById(R.id.familyList);
        timeLayout =  binding.getRoot ().findViewById(R.id.timeList);
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
        presenter.onStart (getActivity ());

    }

    @Override
    public void onAttendRegistered ( boolean result ) {
        if (result) {
            Toast.makeText ( this.getContext (), "登園を記録できました。", Toast.LENGTH_SHORT ).show ();
            viewModel.setStudent ( null );
            timeLayout.removeAllViews ();
            familyLayout.removeAllViews ();
        }else{
            Toast.makeText ( this.getContext (), "登園の記録に失敗しました。", Toast.LENGTH_SHORT ).show ();
        }
    }

    @Subscribe
    public void onScanQR ( QRFragment.QREvent qrEvent ) {
        Student old = viewModel.getStudent ();
        if (old != null) {
            if ( qrEvent.studentId.equals ( old.getStudentId () ) ) {
                return;
            }
        }
//        Student student = new Student ();
//        student.setStudentId ( qrEvent.studentId );
//        viewModel.setStudent ( student );
        // TODO: 2017/12/26 くるくるの表示
        presenter.fetchStudent ( qrEvent.studentId );
    }

    @Override
    public boolean addButton ( Result.Type r, String s ) {
        Button btn = generateButton ( s,r );
        if (r == Result.Type.FAMILY) {
            return familyLayout.addViewWithAnimation( btn , 0);
        }else if (r == Result.Type.TIME) {
            return timeLayout.addViewWithAnimation(btn , 0);
        }
        return false;
    }

    @Override
    public void deleteButton ( Result.Type r ) {
        if (r == Result.Type.FAMILY) {
            familyLayout.removeViewWithAnimation ( familyLayout.getChildAt ( familyLayout.getChildCount () - 1 ) );
        }else if (r == Result.Type.TIME) {
            timeLayout.removeViewWithAnimation ( timeLayout.getChildAt ( timeLayout.getChildCount () - 1 ) );
        }
    }

    private Button generateButton( final String text, final Result.Type type) {
        Button button = new Button ( getActivity () );
        button.setText(text);
        button.setTextColor ( Color.WHITE );

        button.setBackgroundResource ( R.drawable.button_sub_layout );
        button.setOnClickListener ( v -> candSelected ( text,type ) );
        return button;
    }

    void candSelected ( String str, Result.Type type) {
        if (type == Result.Type.FAMILY) {
            viewModel.setSelectedFamily ( str );
        }else if (type == Result.Type.TIME){
            viewModel.setSelectedTime ( str );
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSpeechRecognized ( VoiceTransmitter.SpeechRecognizeEvent event ) {
        Result result = event.getResult ();

        switch ( result.getEnumType () ) {
            case PROVISIONAL:
                viewModel.addDetail ( true, result.content[0] );
                break;
            case FINAL:
                viewModel.addDetail ( false, result.content[0] );
                break;
            case TIME:
                final ArrayList<String> timeQueue = new ArrayList <> (  );
                for ( String received : result.content  ) {
                    if (times.add ( received )) {
                        timeQueue.add ( received );
                        Timber.d("add timeBtn %s", received);
                    }
                }
                if ( timeQueue.isEmpty ()) {
                    return;
                }
                new Async ( this, Result.Type.TIME, timeQueue ).execute ( ) ;
                break;
            case FAMILY:
                final ArrayList<String> familyQueue = new ArrayList <> (  );
                for ( String received : result.content  ) {
                    if (families.add ( received )) {
                        familyQueue.add ( received );
                        Timber.d("add famBtn %s", received);
                    }
                }
                if ( familyQueue.isEmpty ()) {
                    return;
                }
                new Async ( this, Result.Type.FAMILY, familyQueue ).execute ( ) ;
                break;
        }
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
//        Toast.makeText ( getActivity (), "接続に失敗しました。", Toast.LENGTH_SHORT ).show ();
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