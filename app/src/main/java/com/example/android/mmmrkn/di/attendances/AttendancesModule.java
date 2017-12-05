package com.example.android.mmmrkn.di.attendances;

import com.example.android.mmmrkn.presentation.attendances.AttendancesPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 15110016 on 2017/12/05.
 */
@Module
public class AttendancesModule {
	private AttendancesPresenter.Contract contract;
	
	public AttendancesModule (AttendancesPresenter.Contract contract){
		this.contract = contract;
	}
	
	@Provides
	public AttendancesPresenter.Contract provideContract() {
		return contract;
	}
}
