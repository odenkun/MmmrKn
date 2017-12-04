package com.example.android.mmmrkn.di.mode;

import com.example.android.mmmrkn.presentation.mode_select.ModePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 15110016 on 2017/12/04.
 */
@Module
public class ModeModule {
	private ModePresenter.Contract contract;
	
	public ModeModule(ModePresenter.Contract contract){this.contract = contract;}
	
	@Provides
	public ModePresenter.Contract provideContract(){return contract;}
}
