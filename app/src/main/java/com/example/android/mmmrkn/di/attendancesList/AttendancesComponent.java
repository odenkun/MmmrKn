package com.example.android.mmmrkn.di.attendancesList;

import com.example.android.mmmrkn.presentation.attendances.AttendancesActivity;
import com.example.android.mmmrkn.presentation.attendances_list.AttendancesListActivity;

import dagger.Subcomponent;

/**
 * Created by 15110009 on 2017/12/01.
 */

@Subcomponent(modules = {AttendancesModule.class})
public interface AttendancesComponent {
    void inject(AttendancesListActivity a);
}
