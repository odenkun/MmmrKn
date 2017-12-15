package com.example.android.mmmrkn.presentation.attendances_list;

import com.example.android.mmmrkn.infra.api.PartiesService;
import com.example.android.mmmrkn.infra.api.StudentsService;
import com.example.android.mmmrkn.infra.entity.Party;
import com.example.android.mmmrkn.infra.entity.Student;
import com.example.android.mmmrkn.presentation.Presenter;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cookie;
import timber.log.Timber;


public class AttendancesListPresenter extends Presenter{
    private final PartiesService partiesService;
    private Contract contract;


    private AttendancesListRepository attendRepo;
    private PartyRepository partyRepo;
    //Cookieを保持する
    private SharedPrefsCookiePersistor persistor;

    @Inject
    public AttendancesListPresenter(Contract contract, PartiesService partiesService){
        this.contract = contract;
        this.partiesService = partiesService;
    }

    public  void fetchParties(){
        disposables.add (
                partiesService.getParties ()
                        .subscribeOn ( Schedulers.io () )
                        .observeOn ( AndroidSchedulers.mainThread () )
                        .subscribe ( partyList -> {
                            Timber.d ( "party toretayo" );
                            contract.onPartyListFetch ( partyList );
                        }, e -> {
                            Timber.e ( e );
                            contract.onPartyListFetch ( null );
                        } ) );
    }

    public  void fetchEntryList(String partyId){
        disposables.add (
                partiesService.getEntryList(partyId)
                        .subscribeOn ( Schedulers.io () )
                        .observeOn ( AndroidSchedulers.mainThread () )
                        .subscribe ( studentList -> {
                            Timber.d ( "attend とれたよ" );
                            contract.onEntryListFetched (studentList);
                        }, e -> {

                            Timber.e ( e );
                            contract.onEntryListFetched ( null );
                        } ) );
    }

    /**
     * クッキーを消去する
     */
    void clearCookies () {
        Timber.d ( "started" );
        for ( Cookie cookie : persistor.loadAll () ) {
            Timber.d ( cookie.toString () );
        }
        persistor.clear ();
    }

    public  interface  Contract{
        /**
         *
         * @param a 通信結果の保育士の一覧
         */
        void onEntryListFetched ( List<Student> a );
        void onPartyListFetch ( List<Party> a );
    }
}
