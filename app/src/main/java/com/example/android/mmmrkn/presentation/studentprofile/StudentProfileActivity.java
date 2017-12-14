package com.example.android.mmmrkn.presentation.studentprofile;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.databinding.ActivityStudentProfileBinding;
import com.example.android.mmmrkn.infra.entity.Student;

import timber.log.Timber;

public class StudentProfileActivity extends AppCompatActivity {
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStudentProfileBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_student_profile);

        Intent intent = this.getIntent();
        //画面遷移時のstudentIdデータの受け取り処理
        Student student = (Student) intent.getSerializableExtra("student");
        Timber.d(student.toString());
        binding.setStudent(student);
    }
    //画面への操作
}
