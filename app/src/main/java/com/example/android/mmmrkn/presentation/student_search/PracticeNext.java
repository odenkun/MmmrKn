package com.example.android.mmmrkn.presentation.student_search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.mmmrkn.R;

public class PracticeNext extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank);	//このクラスが使われているactivity_nextを指定

        Intent intent = this.getIntent();
        String text = intent.getStringExtra("sendText");
        TextView textView = this.findViewById(R.id.textView);
        textView.setText(text);
    }
}
