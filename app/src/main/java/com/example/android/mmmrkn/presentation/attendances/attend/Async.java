package com.example.android.mmmrkn.presentation.attendances.attend;

import android.os.AsyncTask;

import com.example.android.mmmrkn.infra.voice.Result;

import java.util.ArrayList;

import timber.log.Timber;

public class Async extends AsyncTask<String, String, Void> {
    private Contract contract;
    ArrayList<String> queue;
    Result.Type type;

    int count = 0;

    public Async( Contract listener, Result.Type type, ArrayList<String> queue) {
        this.contract = listener;
        this.queue = queue;
        this.type = type;
    }

    @Override
    protected Void doInBackground(String... arg) {
        while ( !queue.isEmpty () ) {
            publishProgress ( queue.remove ( 0 ) );
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate ( String... values ) {
        super.onProgressUpdate ( values );
        if (!contract.addButton (type,values[0])) {
            if ( ++count < 3) {
                Timber.d("added" + values[0]);
                queue.add ( values[ 0 ] );
            }
        }

    }

    public interface Contract {
        boolean addButton(Result.Type r,String s);
        void deleteButton(Result.Type r);
    }
}