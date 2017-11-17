package com.example.android.mmmrkn.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.api.ApplicationComponent;
import com.example.android.mmmrkn.api.MainComponent;
import com.example.android.mmmrkn.api.MainModule;
import com.example.android.mmmrkn.infra.api.LoginService;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    
    @Inject
    LoginService loginService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        ApplicationComponent appComponent = ((App)getApplication()).component();
        MainComponent mainComponent = appComponent.plus(new MainModule());
        mainComponent.inject(this);
    
    
        findViewById(R.id.textView).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((TextView)view).setText("これが古い書き方、長い");
                    }
                }
        );
        findViewById(R.id.thisistext).setOnClickListener(
                view -> ((TextView)view).setText("これがラムダ式")
        );
        loginService.postTeacherLogin("guriko","guriko")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( voidResponse -> {
                    Log.e("login:", "login dekiatayo");
                });
        
    }
}
