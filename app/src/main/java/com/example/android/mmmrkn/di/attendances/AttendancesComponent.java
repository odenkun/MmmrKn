package com.example.android.mmmrkn.di.attendances;

import com.example.android.mmmrkn.presentation.attendances.AttendancesActivity;

import dagger.Subcomponent;

/**
 * Created by 15110016 on 2017/12/05.
 */
@Subcomponent(modules = {AttendancesModule.class})
public interface AttendancesComponent {
	void inject(AttendancesActivity a);
}
