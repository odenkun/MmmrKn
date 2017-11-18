package com.example.android.mmmrkn.di;

import com.example.android.mmmrkn.presentation.MainPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class MainModule {
    //MVPにおけるビューとプレゼンターの結合度を低くするためのインタフェース
    //ビューであるMainActivityが実装している
    private MainPresenter.Contract contract;

    //MainActivity内で new MainModule(this)のようにインスタンス化される
    public MainModule ( MainPresenter.Contract contract ) {
        this.contract = contract;
    }

    //Activityは保持してはいけないので@Singletonはつけない
    @Provides
    public MainPresenter.Contract provideContract() {
        return contract;
    }
}

