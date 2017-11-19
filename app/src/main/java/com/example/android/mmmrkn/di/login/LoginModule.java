package com.example.android.mmmrkn.di.login;

import com.example.android.mmmrkn.presentation.login.LoginPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class LoginModule {
    //MVPにおけるビューとプレゼンターの結合度を低くするためのインタフェース
    //ビューであるLoginActivityが実装している
    private LoginPresenter.Contract contract;

    //LoginActivity内で new LoginModule(this)のようにインスタンス化される
    public LoginModule ( LoginPresenter.Contract contract ) {
        this.contract = contract;
    }

    //Activityは保持してはいけないので@Singletonはつけない
    @Provides
    public LoginPresenter.Contract provideContract() {
        return contract;
    }
}

