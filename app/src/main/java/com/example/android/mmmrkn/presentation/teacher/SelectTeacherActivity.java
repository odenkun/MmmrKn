package com.example.android.mmmrkn.presentation.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.di.teacher.TeacherModule;
import com.example.android.mmmrkn.infra.entity.Teacher;
import com.example.android.mmmrkn.presentation.App;
import com.example.android.mmmrkn.presentation.mode_select.ModeActivity;


import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

public class SelectTeacherActivity extends AppCompatActivity implements SelectTeacherPresenter.Contract {
    Intent intent;
    @Inject
    SelectTeacherPresenter presenter;


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
	    setContentView ( R.layout.activity_teacher_recycler_base );
       

        ( (App) getApplication () )
                .getComponent ()
                .plus ( new TeacherModule ( this ) )
                .inject ( this );

        presenter.fetchTeachers ();

    }

    //OrmaからのデータをListに代入
    @Override
    public void onTeachersFetched ( List<Teacher> teacherList ) {
        TeacherCardRecyclerView cardRecyclerView = (TeacherCardRecyclerView)findViewById(R.id.teacher_recycle);

        cardRecyclerView.onListFetch(this,teacherList);
        //データをlogとして出力
        for ( Teacher teacher : teacherList ) {
            Timber.d(teacher.toString ());
        }
    }
    @Override
    protected void onDestroy () {
        //通信の結果を受け取らなくする
        presenter.dispose ();
        super.onDestroy ();
    }
}
