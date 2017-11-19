package com.example.android.mmmrkn.presentation.teacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.di.teacher.TeacherModule;
import com.example.android.mmmrkn.infra.entity.Teacher;
import com.example.android.mmmrkn.presentation.App;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

public class SelectTeacherActivity extends AppCompatActivity implements SelectTeacherPresenter.Contract {

    @Inject
    SelectTeacherPresenter presenter;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_select_teacher );
        ( (App) getApplication () )
                .getComponent ()
                .plus ( new TeacherModule ( this ) )
                .inject ( this );

        presenter.fetchTeachers ();
    }

    @Override
    public void onTeachersFetched ( List<Teacher> t ) {
        for ( Teacher teacher : t ) {
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
