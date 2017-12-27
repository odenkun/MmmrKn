package com.example.android.mmmrkn.presentation.attendances.attend;

import android.app.Activity;

import com.example.android.mmmrkn.infra.api.StudentsService;
import com.example.android.mmmrkn.infra.entity.Attendance;
import com.example.android.mmmrkn.infra.entity.Student;
import com.example.android.mmmrkn.infra.voice.VoiceRecorder;
import com.example.android.mmmrkn.infra.voice.VoiceTransmitter;
import com.example.android.mmmrkn.presentation.Presenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import timber.log.Timber;

enum MicMode {
    BLUETOOTH,
    BUILT_IN
}

public class AttendFragmentPresenter extends Presenter
        implements VoiceRecorder.Callback, VoiceTransmitter.Callback {

    private static MicMode mMicMode = MicMode.BUILT_IN;

    private Contract contract;

    private StudentsService studentsService;

    private BTListener mBTListener;
    private VoiceRecorder mVoiceRecorder;
    private VoiceTransmitter mTransmitter;

    //苦肉の策
    private OkHttpClient client;

    @Inject
    public AttendFragmentPresenter ( Contract contract, OkHttpClient client, StudentsService studentsService ) {
        this.contract = contract;
        this.client = client;
        this.studentsService = studentsService;
    }

    void onStart () {
        EventBus.getDefault ().register ( this );
    }

    void onStop () {
        EventBus.getDefault ().unregister ( this );
    }

    //Bluetoothデバイスが接続された時
    @Subscribe
    public void onBTStateChanged ( BTListener.BTEvent btEvent ) {
        if ( mMicMode == MicMode.BLUETOOTH && btEvent.isConnect () ) {
            startRec ();
        }
    }

    //録音の開始
    private void startRec () {
        mVoiceRecorder = new VoiceRecorder ( this );
        int sampleRate = mVoiceRecorder.start ();
        mTransmitter = new VoiceTransmitter ( sampleRate, this, client );
    }

    void readyMic ( Activity activity ) {
        switch ( mMicMode ) {
            case BUILT_IN:
                startRec ();
                break;
            case BLUETOOTH:
                //ブルートゥースで録音するには、
                //ブルートゥースマイクを接続、認識開始してから録音する
                mBTListener = new BTListener ( activity );
                break;
            default:
                Timber.e ( "MicMode is not assigned." );
        }
    }


    //発話開始
    @Override
    public void onVoiceStart () {
        mTransmitter.startRecognize ();
    }

    //発話中
    @Override
    public void onVoice ( byte[] data, int size ) {
        mTransmitter.sendVoice ( data );
    }

    //発話の終了
    @Override
    public void onVoiceEnd () {
        mTransmitter.stopRecognize ();
    }

    //園児リストの受信
    @Override
    public void onStudentReceived ( List <Student> students ) {
        contract.onNameRecognized ( students );
    }

    //通信の失敗
    @Override
    public void onConnectionFailed () {
        contract.onConnectionFailed ();
    }

    void fetchStudent ( String studentId ) {
        if ( studentId == null ) {
            throw new RuntimeException ( "studentId is null" );
        }

        disposables.add (
                studentsService.getStudent ( studentId ).subscribeOn ( Schedulers.io () )
                        .observeOn ( AndroidSchedulers.mainThread () )
                        .subscribe ( student -> {
                            Timber.d ( "生徒とれたよ" );
                            List <Student> list = new ArrayList <> ();
                            list.add ( student );
                            contract.onNameRecognized ( list );
                        }, e -> {
                            Timber.e ( e );
                            contract.onNameRecognized ( null );
                        } )
        );

    }

    void registerAttend ( Student student, String teacherId) {
        disposables.add (
                studentsService.setStudentAttendance (
                        student.getStudentId (),
                        teacherId,
                        new Date ().toString (),
                        student.getAttendance ().getCondition (),
                        student.getAttendance ().getDetail () )
                        .subscribeOn ( Schedulers.io () )
                        .observeOn ( AndroidSchedulers.mainThread () )
                        .subscribe ( () -> {
                            Timber.d ( "登録完了。" );
                            contract.onAttendRegistered ( true );
                        }, e -> {
                            Timber.e ( e );
                            contract.onAttendRegistered ( false );
                        } )
        );
    }

    @Override
    public void dispose () {
        super.dispose ();
        this.contract = null;
        if ( mTransmitter != null ) {
            mTransmitter.close ();
        }
        if ( mVoiceRecorder != null ) {
            mVoiceRecorder.stop ();
        }
        if ( mBTListener != null ) {
            mBTListener.stop ();
        }
    }

    public interface Contract {
        /**
         * 生徒名が認識されAPIから生徒リストが届いた時
         */
        void onNameRecognized ( List <Student> profiles );

        void onAttendRegistered ( boolean result );

        /**
         * 接続に失敗した時
         */
        void onConnectionFailed ();

    }
}
