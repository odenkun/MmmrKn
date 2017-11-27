package com.example.android.mmmrkn.infra.api;

import io.reactivex.Completable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 15110016 on 2017/11/20.
 */

public interface LogoutService {
    //ログアウト
    //通信番号②
    @POST("logout")
    @FormUrlEncoded
    //ログアウトなので引数特になし
    Completable postSchoolLogout();

}
