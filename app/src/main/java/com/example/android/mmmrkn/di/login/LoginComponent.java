package com.example.android.mmmrkn.di.login;

import com.example.android.mmmrkn.presentation.login.LoginActivity;

import dagger.Subcomponent;

//SubcomponentはActivityやFragmentごとに作るもの
//modules={}の中には使用するモジュールの一覧を書く
@Subcomponent(modules = {LoginModule.class})
public interface LoginComponent {

    //Activityはコンストラクタをオーバーライドできないので、
    //このinjectメソッドによって、@Injectが付いたメンバ変数に、MainModuleによって代入する
    void inject(LoginActivity m);
}