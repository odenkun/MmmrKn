package com.example.android.mmmrkn.di.attend;

import com.example.android.mmmrkn.presentation.attend.AttendPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class AttendModule {
    private AttendPresenter.Contract contract;

    public AttendModule ( AttendPresenter.Contract contract ) {
        this.contract = contract;
    }

    @Provides
    public AttendPresenter.Contract provideContract() {
        return contract;
    }
}