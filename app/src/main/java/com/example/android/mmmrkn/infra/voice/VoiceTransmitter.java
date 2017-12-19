package com.example.android.mmmrkn.infra.voice;

import android.util.Log;

import com.example.android.mmmrkn.infra.entity.StudentProfile;
import com.example.android.mmmrkn.presentation.App;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import timber.log.Timber;

enum Header {
    RECOGNIZE ( "recognize" );
    private final String name;

    Header ( final String name ) {
        this.name = name;
    }

    public String getName () {
        return this.name;
    }
}

enum WebSocketState {
    PREPARATION,
    CLOSED,
    OPENED,
    RECOGNIZING
}

public class VoiceTransmitter extends WebSocketListener {
    private static final String SUB_PROTOCOL_HEADER = "Sec-WebSocket-Protocol";
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private WebSocket ws;
    private int sampleRate;
    private static final String WS_URL = "http://192.168.1.2:6001";
    private WebSocketState mState = WebSocketState.PREPARATION;

    private Callback callback;

    public VoiceTransmitter ( int sampleRate, Callback callback, OkHttpClient client) {

        this.sampleRate = sampleRate;
        this.callback = callback;

        Request request = new Request.Builder ()
                .addHeader ( SUB_PROTOCOL_HEADER, Header.RECOGNIZE.getName () )
                .url ( WS_URL )
                .build ();

        ws = client.newWebSocket ( request, this );
    }

    /**
     * WebSocketコネクションが確立されたとき呼ばれる
     */
    @Override
    public void onOpen ( WebSocket webSocket, Response response ) {
        Timber.d ( "opened" );
        ws = webSocket;
        ws.send ( "sampleRate:" + sampleRate );
        mState = WebSocketState.OPENED;
    }

    @Override
    public void onMessage ( WebSocket webSocket, String text ) {
        Timber.d ( "Receiving : %s", text );

    }

    @Override
    public void onMessage ( WebSocket webSocket, ByteString bytes ) {
        Timber.d ( "Receiving bytes : %s", bytes.hex () );
    }

    /**
     *音声認識を始める
     */
    public boolean startRecognize () throws IllegalStateException {

        if (! checkState ( WebSocketState.OPENED )) {
            return false;
        }
        boolean result = this.send ( "RecoBegin" );
        if ( result ) {
            mState = WebSocketState.RECOGNIZING;
            Timber.d ( "recognizing started" );
        } else {
            Timber.d ( "recognizing cannot start" );
        }
        return result;
    }

    /**
     *音声認識を終える
     */
    public boolean stopRecognize () throws IllegalStateException {
        if (! checkState ( WebSocketState.RECOGNIZING )) {
            return false;
        }
        boolean result = this.send ( "RecoEnd" );
        if ( result ) {
            Timber.d ( "recognizing has been successfully stopped" );
            mState = WebSocketState.OPENED;
        }
        return result;
    }

    public boolean close () throws IllegalStateException {
        if (! checkState ( WebSocketState.OPENED, WebSocketState.RECOGNIZING )) {
            return false;
        }
        //wsはonClosingでnullになるので問題ない
        return ws.close ( 1000, null );
    }

    //切断時必ず実行される
    @Override
    public void onClosing ( WebSocket webSocket, int code, String reason ) {
        mState = WebSocketState.CLOSED;
        ws = null;
        webSocket.close ( NORMAL_CLOSURE_STATUS, null );
        Timber.d ( "Closing : " + code + " / " + reason );
    }

    @Override
    public void onFailure ( WebSocket webSocket, Throwable t, Response response ) {
        Timber.e (t);
        callback.onConnectionFailed ();
    }

    public boolean sendVoice ( final byte[] data ) throws IllegalStateException {
        if (! (checkState ( WebSocketState.OPENED,WebSocketState.RECOGNIZING ))) {
            return false;
        }
        boolean result = ws.send ( ByteString.of ( data ) );
        if ( !result ) {
            Timber.e ("cannot enqueue audio data" );
        }
        return result;
    }

    boolean send ( final String message ) throws IllegalStateException {
        if (! checkState ( WebSocketState.OPENED, WebSocketState.RECOGNIZING )) {
            return false;
        }
        boolean result = ws.send ( message );
        if ( !result ) {
            Timber.e ( "cannot enqueue a message" );
        }
        return result;
    }

    private boolean checkState ( WebSocketState... accepts ) throws IllegalStateException {
        if (mState == WebSocketState.PREPARATION) {
            return false;
        }
        for ( WebSocketState accept : accepts ) {
            if ( mState == accept ) {
                return true;
            }
        }
        throw new IllegalStateException ( "WebSocket state is " + mState.name () );
    }

    public interface Callback {
        void onStudentReceived( List<StudentProfile> students);
        void onConnectionFailed();
    }
}