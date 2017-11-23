package com.example.android.mmmrkn.infra.repository;

import com.example.android.mmmrkn.infra.api.TeacherService;
import com.example.android.mmmrkn.infra.entity.OrmaDatabase;
import com.example.android.mmmrkn.infra.entity.Teacher;
import com.github.gfx.android.orma.Inserter;
import com.github.gfx.android.orma.annotation.OnConflict;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Single;
import timber.log.Timber;

public class TeacherRepository {
    private TeacherService teacherService;
    private OrmaDatabase ormaDatabase;

    @Inject
    public TeacherRepository ( TeacherService teacherService, OrmaDatabase ormaDatabase ) {
        this.teacherService = teacherService;
        this.ormaDatabase = ormaDatabase;
    }

    public Single <List <Teacher>> getTeachers () {

        Single <List <Teacher>> single = Single.create ( e -> e.onSuccess ( ormaDatabase.selectFromTeacher ().toList () ) );
        single = single.flatMap ( cachedList -> {
            //キャッシュに存在しない
            if ( cachedList == null || cachedList.isEmpty () ) {
                Timber.d("teacher cache doesn't exist");
                return teacherService.getTeachers ()
                        //成功時はキャッシュする
                        .doOnSuccess ( teachers -> {
                            Timber.d("start insert");
                            Inserter <Teacher> inserter = ormaDatabase.prepareInsertIntoTeacher ( OnConflict.REPLACE );
                            inserter.executeAll ( teachers );
                        } );
            } else {
                Timber.d("teacher cache exists");
                //キャッシュに存在したのでそのまま返す
                return Single.just ( cachedList );
            }
        } );
        return single;
    }
}
