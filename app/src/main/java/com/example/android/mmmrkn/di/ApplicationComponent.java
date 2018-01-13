package com.example.android.mmmrkn.di;


import com.example.android.mmmrkn.di.attendances.AttendancesComponent;
import com.example.android.mmmrkn.di.attendances.AttendancesModule;
import com.example.android.mmmrkn.di.attendancesList.AttendancesListComponent;
import com.example.android.mmmrkn.di.attendancesList.AttendancesListModule;
import com.example.android.mmmrkn.di.login.LoginComponent;
import com.example.android.mmmrkn.di.login.LoginModule;
import com.example.android.mmmrkn.di.mode.ModeComponent;
import com.example.android.mmmrkn.di.mode.ModeModule;
import com.example.android.mmmrkn.di.start.StartComponent;
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
    AttendancesListComponent plus(AttendancesListModule al);
    ModeComponent plus(ModeModule n);
    AttendancesComponent plus(AttendancesModule a);
}
