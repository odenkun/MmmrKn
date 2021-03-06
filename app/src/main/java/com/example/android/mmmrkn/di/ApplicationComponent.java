package com.example.android.mmmrkn.di;


import com.example.android.mmmrkn.di.attendancesList.AttendancesComponent;
import com.example.android.mmmrkn.di.attendancesList.AttendancesModule;
import com.example.android.mmmrkn.di.login.LoginComponent;
import com.example.android.mmmrkn.di.login.LoginModule;
import com.example.android.mmmrkn.di.mode.ModeComponent;
import com.example.android.mmmrkn.di.mode.ModeModule;
import com.example.android.mmmrkn.di.start.StartComponent;
import com.example.android.mmmrkn.di.student_profile.ProfileComponent;
import com.example.android.mmmrkn.di.student_profile.ProfileModule;
import com.example.android.mmmrkn.di.teacher.TeacherComponent;
import com.example.android.mmmrkn.di.teacher.TeacherModule;
import com.example.android.mmmrkn.presentation.attendances_list.AttendancesDialog;

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
    AttendancesComponent plus(AttendancesModule a);
    ModeComponent plus(ModeModule n);
    void inject(AttendancesDialog d);
}
