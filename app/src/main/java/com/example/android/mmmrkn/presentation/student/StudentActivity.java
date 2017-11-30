package com.example.android.mmmrkn.presentation.student;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.mmmrkn.R;

import javax.inject.Inject;

/**
 * Created by 15110016 on 2017/11/29.
 */

public class StudentActivity extends AppCompatActivity{
	@Inject
	StudentPresenter presenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student);
		
	}
	
}
