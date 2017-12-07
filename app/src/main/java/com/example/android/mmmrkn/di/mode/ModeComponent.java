package com.example.android.mmmrkn.di.mode;

import com.example.android.mmmrkn.presentation.mode_select.ModeActivity;

import dagger.Subcomponent;

/**
 * Created by 15110016 on 2017/12/04.
 */
@Subcomponent(modules = {ModeModule.class})
public interface ModeComponent {
	void inject(ModeActivity m);
}
