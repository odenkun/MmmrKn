package com.example.android.mmmrkn.presentation.party;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.di.teacher.TeacherModule;
import com.example.android.mmmrkn.presentation.App;
import com.example.android.mmmrkn.presentation.login.LoginPresenter;

import javax.inject.Inject;

/**
 * Created by 15110009 on 2017/11/27.
 */

public class SelectPartyActyvity extends AppCompatActivity  {

    @Inject
    SelectPartyPresenter presenter;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_teacher_recycler_base );
        

    }

}
