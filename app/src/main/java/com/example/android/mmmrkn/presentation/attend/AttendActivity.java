package com.example.android.mmmrkn.presentation.attend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.di.attend.AttendModule;
import com.example.android.mmmrkn.presentation.App;


import javax.inject.Inject;

import timber.log.Timber;

public class AttendActivity extends AppCompatActivity implements AttendPresenter.Contract {

    @Inject
    AttendPresenter presenter;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_attend );

        ( (App) getApplication () )
                .getComponent ()
                .plus ( new AttendModule ( this ) )
                .inject ( this );
        presenter.tokenize ();
    }


}
