package com.example.android.mmmrkn.presentation.studentprofile;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.databinding.ActivityStudentProfileBinding;
import com.example.android.mmmrkn.infra.entity.Student;
import com.example.android.mmmrkn.presentation.attendances_list.AttendancesListActivity;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

public class StudentProfileActivity extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStudentProfileBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_student_profile);
        Intent intent = this.getIntent();
        //画面遷移時のstudentIdデータの受け取り処理
        Student student = (Student) intent.getSerializableExtra("student");
        Timber.d(student.toString());
        binding.setStudent(student);
        ImageView pic = this.findViewById(R.id.image_picture);
        Picasso.with(context)
                .load("https://mmmr-mock-api.mybluemix.net/images/students/" + student.getPicturePath())
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .transform(new RoundedTransformationBuilder()
                        .borderColor(R.color.womanFrame)
                        .borderWidthDp(3)
                        .oval(true)
                        .build())
                .into(pic);
    }
    //画面への操作

}
