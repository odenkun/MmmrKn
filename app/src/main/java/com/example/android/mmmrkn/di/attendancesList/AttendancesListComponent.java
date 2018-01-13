package com.example.android.mmmrkn.di.attendancesList;

import com.example.android.mmmrkn.presentation.attendances_list.AttendancesListActivity;
import com.example.android.mmmrkn.presentation.attendances_list.students.AttendancesFragment;

import dagger.Subcomponent;

/**
 * Created by 15110009 on 2017/12/01.
 */

@Subcomponent(modules = {AttendancesListModule.class})
public interface AttendancesListComponent {
    void inject(AttendancesFragment a);
}
