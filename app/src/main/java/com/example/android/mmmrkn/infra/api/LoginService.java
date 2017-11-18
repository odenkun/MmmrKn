package com.example.android.mmmrkn.infra.api;


import io.reactivex.Completable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    //POSTメソッドでbaseURL/loginにリクエスト
    @POST("login")
    //
    @FormUrlEncoded
    //今回はログイン通信に結果がないことから、返り値にCompletableを設定
    Completable postTeacherLogin( @Field("userId")String SchoolId,
                                  @Field("password")String pass);
}
