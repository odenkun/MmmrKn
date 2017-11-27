package com.example.android.mmmrkn.infra.api;

import com.example.android.mmmrkn.infra.entity.Teacher;
import java.util.List;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface TeacherService {
    //教員の一覧を取得
    //通信番号③
    @GET ("teachers")
    //応答が一回きり
    Single<List<Teacher>> getTeachers( );

    //教員詳細を取得
    //通信番号④
    @GET("teachers/{teachersId}")
    Single<Teacher> getTeacher();
}
