package com.example.android.mmmrkn.di.gohome;

import com.example.android.mmmrkn.presentation.gohome.GoHomePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 15110016 on 2017/12/05.
 */
@Module
public class GoHomeModule {
	
	private GoHomePresenter.Contract contract;
	
	public GoHomeModule(GoHomePresenter.Contract contract){
		this.contract = contract;
	}
	
	@Provides
	public GoHomePresenter.Contract provideContract() {
		return contract;
	}
}
