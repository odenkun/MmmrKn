package com.example.android.mmmrkn.infra.api;

import com.example.android.mmmrkn.infra.entity.Attendances;
import com.example.android.mmmrkn.infra.entity.Party;
import com.example.android.mmmrkn.infra.entity.SearchResult;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by 15110016 on 2017/11/20.
 */

public interface PartiesService {
    //園ごとのクラス一覧の取得
    //通信番号⑤
    @GET("parties")
    Single<List<Party>> getParties();

    //クラスの詳細(IDとクラス名)取得
    //通信番号⑥
    @GET("parties/{partyId}")
    Single<List<Party>>getParty(@Path("partyId") String PartyId);


    //クラスごとの当日の園児たちの登降園記録の一覧取得
    //通信番号⑦
    @GET("parties/{partyId}/student")
    Single<List<Attendances>>getEntryList(@Path("partyId") String PartyId);

    //あだ名・本名から推測される園児の一覧取得
    //通信番号⑫
    @POST("parties/{partyId}student/assume")
    Single<List<SearchResult>>searchStudents(@Path("partyId") String PartyId,
                                             @Field("SearchName") String SearchName);


}
