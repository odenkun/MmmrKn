package com.example.android.mmmrkn.infra.api;

import com.example.android.mmmrkn.infra.entity.Teacher;
import java.util.List;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface TeacherService {
    @GET ("teachers")
    //応答が一回きり
    Single<List<Teacher>> getTeachers( );
}
