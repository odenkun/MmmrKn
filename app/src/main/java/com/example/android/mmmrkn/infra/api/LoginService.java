package com.example.android.mmmrkn.infra.api;


import io.reactivex.Completable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @POST("login")
    @FormUrlEncoded
    Completable postTeacherLogin( @Field("userId")String SchoolId,
                                  @Field("password")String pass);
}
