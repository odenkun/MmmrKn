package com.example.android.mmmrkn.presentation.attendances_list;

import android.content.Context;

import com.example.android.mmmrkn.infra.entity.Attendances;
import com.example.android.mmmrkn.infra.entity.Party;
import com.example.android.mmmrkn.infra.repository.AttendancesListRepository;
import com.example.android.mmmrkn.infra.repository.PartyRepository;
import com.example.android.mmmrkn.presentation.Presenter;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class AttendancesListPresenter extends Presenter{
    private Contract contract;

    private AttendancesListRepository attendRepo;
    private PartyRepository partyRepo;

    @Inject
    public AttendancesListPresenter(Contract contract, AttendancesListRepository attendancesListRepository, PartyRepository partyRepository){
        this.contract = contract;
        this.attendRepo = attendancesListRepository;
        this.partyRepo = partyRepository;
    }

    public  void fetchParties(){
        disposables.add (
                partyRepo.getParties ()
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
                attendRepo.getEntryList(partyId)
                        .subscribeOn ( Schedulers.io () )
                        .observeOn ( AndroidSchedulers.mainThread () )
                        .subscribe ( attendancesList -> {
                            Timber.d ( "attend とれたよ" );
                            contract.onEntryListFetched ( attendancesList );
                        }, e -> {
                            Timber.e ( e );
                            contract.onEntryListFetched ( null );
                        } ) );
    }

    public  interface  Contract{
        /**
         *
         * @param a 通信結果の保育士の一覧
         */
        void onEntryListFetched ( List<Attendances> a );
        void onPartyListFetch ( List<Party> a );
    }
}
