package com.example.android.mmmrkn.infra.repository;

import com.example.android.mmmrkn.infra.api.PartiesService;
import com.example.android.mmmrkn.infra.entity.Attendances;
import com.example.android.mmmrkn.infra.entity.OrmaDatabase;
import com.github.gfx.android.orma.Inserter;
import com.github.gfx.android.orma.annotation.OnConflict;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import timber.log.Timber;

/**
 * Created by 15110009 on 2017/12/01.
 */

public class AttendancesListRepository {
    private PartiesService partiesService;
    private OrmaDatabase ormaDatabase;

    @Inject
    public  AttendancesListRepository(PartiesService partiesService,OrmaDatabase ormaDatabase) {
    this.ormaDatabase = ormaDatabase;
    this.partiesService = partiesService;
    }
    public  Single<List<Attendances>> getEntryList(String partyId){

        Single<List<Attendances>> single = Single.create(e -> e.onSuccess(ormaDatabase.selectFromAttendances().toList()));
        single = single.flatMap( cachedList ->{
            //キャッシュに保存しない
            if(cachedList == null || cachedList.isEmpty()){
                Timber.d("attendancesList cache doesn't exist");
                return partiesService.getEntryList(partyId)
                        //成功時はキャシュ
                        .doOnSuccess( attendances -> {
                            Timber.d("start insert");
                            Inserter<Attendances> inserter = ormaDatabase.prepareInsertIntoAttendances(OnConflict.REPLACE);
                            inserter.executeAll(attendances);
                        }) ;
            } else{
                Timber.d("attendancesList cache exists");
                //キャッシュに保存したのでそのまま返す
                return Single.just(cachedList);
            }
        } );
        return  single;
    }

}
