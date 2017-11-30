package com.example.android.mmmrkn.presentation.studioprofile;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.databinding.ActivityStudentProfileBinding;
import com.example.android.mmmrkn.di.ApplicationComponent;
import com.example.android.mmmrkn.di.student_profile.ProfileComponent;
import com.example.android.mmmrkn.di.student_profile.ProfileModule;
import com.example.android.mmmrkn.infra.entity.StudentProfile;
import com.example.android.mmmrkn.presentation.App;

import javax.inject.Inject;

public class StudentProfileActivity extends AppCompatActivity implements StudentProfilePresenter.Contract {

    @Inject
    StudentProfilePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        ApplicationComponent appComponent = ( (App) getApplication () ).getComponent ();

        ProfileComponent profileComponent =  appComponent.plus(new ProfileModule(this));
        Intent intent = getIntent();
        //画面遷移時のstudentIdデータの受け取り処理
        String studentId = intent.getStringExtra("studentId");

        presenter.fetchProfile(studentId);
    }
    //画面への操作
    @Override
    public void onFetchComplete(StudentProfile studentProfile){
        ActivityStudentProfileBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_student_profile);
        //2017/11/24
        binding.setStudentProfile(studentProfile);
    }

    @Override
    protected  void onDestroy(){
        presenter.dispose();
        super.onDestroy();
    }
}
