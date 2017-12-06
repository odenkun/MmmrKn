package com.example.android.mmmrkn.di.attendancesList;

import com.example.android.mmmrkn.presentation.attendances_list.AttendancesPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 15110009 on 2017/12/01.
 */
@Module
public class AttendancesModule {
    private AttendancesPresenter.Contract contract;
    public AttendancesModule(AttendancesPresenter.Contract contract){this.contract=contract;}
    @Provides
    public  AttendancesPresenter.Contract provideContract(){return  contract;}
}
