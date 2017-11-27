package com.example.android.mmmrkn.di;

import com.example.android.mmmrkn.di.login.LoginComponent;
import com.example.android.mmmrkn.di.login.LoginModule;
import com.example.android.mmmrkn.di.start.StartComponent;
import com.example.android.mmmrkn.di.student_profile.ProfileComponent;
import com.example.android.mmmrkn.di.student_profile.ProfileModule;
import com.example.android.mmmrkn.di.teacher.TeacherComponent;
import com.example.android.mmmrkn.di.teacher.TeacherModule;

import javax.inject.Singleton;

import dagger.Component;

//
@Singleton
//ComponentはSubomponentの親
//modules={}の中には使用するモジュールの一覧を書く
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    LoginComponent plus( LoginModule m);
    StartComponent plus();
    TeacherComponent plus( TeacherModule t);
    ProfileComponent plus(ProfileModule p);
}
