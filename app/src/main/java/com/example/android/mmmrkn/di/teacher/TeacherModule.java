package com.example.android.mmmrkn.di.teacher;

import com.example.android.mmmrkn.presentation.teacher.SelectTeacherPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class TeacherModule {
    private final SelectTeacherPresenter.Contract contract;
    public TeacherModule ( SelectTeacherPresenter.Contract contract ) {
        this.contract = contract;
    }
    @Provides
    public SelectTeacherPresenter.Contract provideContract() {
        return contract;
    }
}
