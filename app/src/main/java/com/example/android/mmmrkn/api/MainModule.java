package com.example.android.mmmrkn.api;

import com.example.android.mmmrkn.infra.api.LoginService;
import com.example.android.mmmrkn.presentation.MainActivity;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import retrofit2.Retrofit;


@Module
public class MainModule {
    @Inject
    Retrofit retrofit;
    
    LoginService loginService;
    
    public MainModule() {
        this.loginService = retrofit.create(LoginService.class);
    }
    
    //ログイン用のサービス生成
    @Provides
    public LoginService provideLoginService(Retrofit retrofit) {
        return loginService;
    }
}

