package com.example.android.mmmrkn.di.attendancesList;

import com.example.android.mmmrkn.presentation.attendances_list.AttendancesListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 15110009 on 2017/12/01.
 */
@Module
public class AttendancesListModule {
    private final AttendancesListPresenter.Contract contract;
    public AttendancesListModule(AttendancesListPresenter.Contract contract){this.contract=contract;}
    @Provides
    public  AttendancesListPresenter.Contract provideContract(){return  contract;}
}
