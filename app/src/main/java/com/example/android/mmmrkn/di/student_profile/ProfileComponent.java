package com.example.android.mmmrkn.di.student_profile;

import com.example.android.mmmrkn.presentation.login.LoginActivity;
import com.example.android.mmmrkn.presentation.studioprofile.StudentProfileActivity;

import dagger.Subcomponent;

//SubcomponentはActivityやFragmentごとに作るもの
//modules={}の中には使用するモジュールの一覧を書く
@Subcomponent(modules = {ProfileModule.class})
public interface ProfileComponent {

    //Activityはコンストラクタをオーバーライドできないので、
    //このinjectメソッドによって、@Injectが付いたメンバ変数に、MainModuleによって代入する
    void inject(StudentProfileActivity m);
}