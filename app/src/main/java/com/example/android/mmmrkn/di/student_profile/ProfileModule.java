package com.example.android.mmmrkn.di.student_profile;

import com.example.android.mmmrkn.presentation.studioprofile.StudentProfilePresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class ProfileModule {
    //MVPにおけるビューとプレゼンターの結合度を低くするためのインタフェース
    //ビューであるLoginActivityが実装している
    private StudentProfilePresenter.Contract contract;

    //LoginActivity内で new ProfileModule(this)のようにインスタンス化される
    public ProfileModule(StudentProfilePresenter.Contract contract ) {
        this.contract = contract;
    }

    //Activityは保持してはいけないので@Singletonはつけない
    @Provides
    public StudentProfilePresenter.Contract provideContract() {
        return contract;
    }
}

