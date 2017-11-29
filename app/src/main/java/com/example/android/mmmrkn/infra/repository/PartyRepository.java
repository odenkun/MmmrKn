package com.example.android.mmmrkn.infra.repository;

import com.example.android.mmmrkn.infra.api.PartiesService;
import com.example.android.mmmrkn.infra.entity.OrmaDatabase;
import com.example.android.mmmrkn.infra.entity.Party;
import com.github.gfx.android.orma.Inserter;
import com.github.gfx.android.orma.annotation.OnConflict;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import timber.log.Timber;

/**
 * Created by 15110009 on 2017/11/29.
 */

public class PartyRepository {
    private PartiesService partiesService;
    private OrmaDatabase ormaDatabase;

    @Inject
    public  PartyRepository(PartiesService partiesService, OrmaDatabase ormaDatabase){
        this.ormaDatabase = ormaDatabase;
        this.partiesService = partiesService;

    }

    public Single<List<Party>> getParties(){

        Single<List<Party>> single = Single.create(e -> e.onSuccess(ormaDatabase.selectFromParty().toList()));
        single = single.flatMap( cachedList ->{
            //キャッシュに保存しない
            if(cachedList == null || cachedList.isEmpty()){
                Timber.d("party cache doesn't exist");
                return partiesService.getParties()
                        //成功時はキャシュ
                        .doOnSuccess( parties -> {
                            Timber.d("start insert");
                            Inserter<Party> inserter = ormaDatabase.prepareInsertIntoParty(OnConflict.REPLACE);
                            inserter.executeAll(parties);
                        }) ;
            } else{
                Timber.d("party cache exists");
                //キャッシュに保存したのでそのまま返す
                return Single.just(cachedList);
            }
        } );
        return  single;
    }


}
