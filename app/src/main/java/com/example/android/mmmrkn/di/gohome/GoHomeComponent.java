package com.example.android.mmmrkn.di.gohome;

import com.example.android.mmmrkn.presentation.gohome.GoHomeFragment;

import dagger.Subcomponent;

/**
 * Created by 15110016 on 2017/12/05.
 */
@Subcomponent(modules = {GoHomeModule.class})
public interface GoHomeComponent {
	void inject(GoHomeFragment g);
}