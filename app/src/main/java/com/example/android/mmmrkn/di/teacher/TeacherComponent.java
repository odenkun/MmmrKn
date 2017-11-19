package com.example.android.mmmrkn.di.teacher;

import com.example.android.mmmrkn.di.login.LoginModule;
import com.example.android.mmmrkn.presentation.teacher.SelectTeacherActivity;

import dagger.Subcomponent;

/**
 * Created by kouj1en on 2017/11/19.
 */

@Subcomponent(modules = {TeacherModule.class})
public interface TeacherComponent {
    void inject(SelectTeacherActivity s);
}
