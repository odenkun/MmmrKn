package com.example.android.mmmrkn.presentation.attendances;

import android.app.Activity;

import com.example.android.mmmrkn.infra.entity.StudentProfile;
import com.example.android.mmmrkn.infra.voice.VoiceRecorder;
import com.example.android.mmmrkn.infra.voice.VoiceTransmitter;
import com.example.android.mmmrkn.presentation.Presenter;

import java.util.List;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import timber.log.Timber;

enum MicMode {
    BLUETOOTH,
    BUILT_IN
}

public class AttendancesPresenter extends Presenter
        implements BTListener.Callback,VoiceRecorder.Callback,VoiceTransmitter.Callback  {

    private static MicMode mMicMode = MicMode.BUILT_IN;

    private Contract contract;

    private BTListener mBTListener;
    private VoiceRecorder mVoiceRecorder;
    private VoiceTransmitter mTransmitter;

    //苦肉の策
    private OkHttpClient client;

    @Inject
    public AttendancesPresenter ( Contract contract, OkHttpClient client) {
        this.contract = contract;
        this.client = client;
    }

    //Bluetoothデバイスが接続された時
    @Override
    public void onBTDeviceConnected () {
        if ( mMicMode == MicMode.BLUETOOTH ) {
            startRec ();
        }
    }

    @Override
    public void onBTDeviceDisconnected () {
        if (mMicMode == MicMode.BLUETOOTH) {
            contract.onBTDeviceDisconnected ();
        }
    }


    //録音の開始
    private void startRec() {
        mVoiceRecorder = new VoiceRecorder(this);
        int sampleRate = mVoiceRecorder.start();
        mTransmitter = new VoiceTransmitter(sampleRate,this,client);
    }

    void readyMic ( Activity activity ) {
        switch ( mMicMode ) {
            case BUILT_IN:
                startRec ();
                break;
            case BLUETOOTH:
                //ブルートゥースで録音するには、
                //ブルートゥースマイクを接続、認識開始してから録音する
                mBTListener = new BTListener ( activity, this );
                break;
            default:
                Timber.e ( "MicMode is not assigned." );
        }
    }


    //発話開始
    @Override
    public void onVoiceStart() {
        mTransmitter.startRecognize ();
    }

    //発話中
    @Override
    public void onVoice(byte[] data, int size) {
        mTransmitter.sendVoice (data);
    }

    //発話の終了
    @Override
    public void onVoiceEnd() {
        mTransmitter.stopRecognize();
    }

    //園児リストの受信
    @Override
    public void onStudentReceived ( List <StudentProfile> students ) {
        contract.onNameRecognized ( students );
    }
    //通信の失敗
    @Override
    public void onConnectionFailed () {
        contract.onConnectionFailed ();
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
        void onNameRecognized ( List <StudentProfile> profiles );

        /**
         * 接続に失敗した時
         */
        void onConnectionFailed ();
        void onBTDeviceDisconnected();
    }


}
