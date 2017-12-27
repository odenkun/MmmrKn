package com.example.android.mmmrkn.infra.api;

import com.example.android.mmmrkn.infra.entity.Attendance;
import com.example.android.mmmrkn.infra.entity.Student;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by 15110016 on 2017/11/20.
 */

public interface StudentsService {
    //園児のプロフィール取得
    //通信番号⑧
    @GET("students/{studentId}")
    Single<Student> getStudent(@Path("studentId")String StudentId);

    // 園児の指定した日付の登園ログをすべて取得
    //通信番号⑨
    @GET("students/{studentId}/date/{date}")
    Single<Student> getAttendanceLog(@Path("studentId")String studentId,
                                        @Path("date")String date);


    //園児の登園の登録
    //通信番号⑩
    @POST("students/{studentId}/attendance")
    @FormUrlEncoded
    Completable setStudentAttendance(@Path("studentId")String StudentId,
                                     @Field("teacherId") String TeacherId,
                                     @Field("time")String Time,
                                     @Field("condition")String Condition,
                                     @Field("detail")String Details);

    //園児の降園の登録
    //通信番号⑪
    @POST("students/{studentId}/goHome")
    @FormUrlEncoded
    Completable setStudentgoHome(@Path("studentId")String StudentId,
                                     @Field("teacherId") String TeacherId,
                                     @Field("time")String Time);



}
