package com.example.android.mmmrkn.infra.api;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @POST("schools/login")
    @FormUrlEncoded
    Observable<Response<Void>> postTeacherLogin(@Field("SchoolId")String SchoolId,
                                                @Field("pass")String pass);
}
