package com.example.android.mmmrkn.di.attendances;

import com.example.android.mmmrkn.presentation.attendances.attend.AttendFragmentPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 15110016 on 2017/12/05.
 */
@Module
public class AttendancesModule {
	private AttendFragmentPresenter.Contract contract;
	
	public AttendancesModule (AttendFragmentPresenter.Contract contract){
		this.contract = contract;
	}
	
	@Provides
	public AttendFragmentPresenter.Contract provideContract() {
		return contract;
	}
}
