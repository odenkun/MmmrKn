package com.example.android.mmmrkn.presentation.attendances.attend;

import android.os.AsyncTask;

import com.example.android.mmmrkn.infra.voice.Result;

import java.util.ArrayList;

public class Async extends AsyncTask<String, String, Void> {
    private Contract contract;
    ArrayList<String> queue;
    Result.Type type;

    public Async( Contract listener, Result.Type type, ArrayList<String> queue) {
        this.contract = listener;
        this.queue = queue;
        this.type = type;
    }

    @Override
    protected Void doInBackground(String... arg) {
        while ( !queue.isEmpty () ) {
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
            }
            publishProgress ( queue.remove ( 0 ) );
        }
        return null;
    }

    @Override
    protected void onProgressUpdate ( String... values ) {
        super.onProgressUpdate ( values );
        contract.addButton (type,values[0]);
    }

    public interface Contract {
        void addButton(Result.Type r,String s);
        void deleteButton(Result.Type r);
    }
}