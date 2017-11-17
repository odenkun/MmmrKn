package com.example.android.mmmrkn.di;

import com.example.android.mmmrkn.presentation.MainPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class MainModule {
    //Contractとは、MVPにおけるビューとプレゼンターの結合度を低くするためのもの
    //ビューであるMainActivityが実装している

    MainPresenter.Contract contract;


    public MainModule ( MainPresenter.Contract contract ) {
        this.contract = contract;
    }

    //Daggerにおける、変数を提供するためのアノテーション
    @Provides
    public MainPresenter.Contract provideContract() {
        return contract;
    }
}

