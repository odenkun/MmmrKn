package com.example.android.mmmrkn.di;

import com.example.android.mmmrkn.presentation.MainActivity;

import dagger.Subcomponent;

//SubcomponentはActivityやFragmentごとに作るもの
//modules={}の中には使用するモジュールの一覧を書く
@Subcomponent(modules = {MainModule.class})
public interface MainComponent{

    //Activityはコンストラクタをオーバーライドできないので、
    //このinjectメソッドによって、@Injectが付いたメンバ変数に、MainModuleによって代入する
    void inject(MainActivity m);
}