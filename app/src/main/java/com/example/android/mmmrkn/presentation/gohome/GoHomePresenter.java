package com.example.android.mmmrkn.presentation.gohome;

import com.example.android.mmmrkn.presentation.Presenter;

/**
 * Created by 15110016 on 2017/12/05.
 */

public class GoHomePresenter extends Presenter {
	
	private Contract contract;
	
	//参照の切断
	@Override
	public void dispose(){
		super.dispose();
		this.contract = null;
	}
	public interface Contract{
	
	}
}
