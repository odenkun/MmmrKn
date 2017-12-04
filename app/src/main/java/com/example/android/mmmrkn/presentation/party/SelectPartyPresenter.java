package com.example.android.mmmrkn.presentation.party;

import com.example.android.mmmrkn.infra.api.PartiesService;
import com.example.android.mmmrkn.infra.entity.Party;
import com.example.android.mmmrkn.infra.repository.PartyRepository;
import com.example.android.mmmrkn.presentation.Presenter;
import com.example.android.mmmrkn.presentation.login.LoginPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by 15110009 on 2017/11/27.
 */

public class SelectPartyPresenter  extends Presenter {

    private Contract contract;

    private PartyRepository partyRepository;

    @Inject
    public SelectPartyPresenter(Contract contract, PartyRepository partyRepository){
        this.contract = contract;
        this.partyRepository = partyRepository;
    }
    /**
     * 組の取得
     */
    public  void fetchParties(){
        disposables.add (
                partyRepository.getParties ()
                        .subscribeOn ( Schedulers.io () )
                        .observeOn ( AndroidSchedulers.mainThread () )
                        .subscribe ( partyList -> {
                            Timber.d ( "組一覧 とれたよ" );
                            contract.onPartiesFetched ( partyList );
                        }, e -> {
                            Timber.e ( e );
                            contract.onPartiesFetched ( null );
                        } ) );
    }
    public interface Contract {
        /**
         *
         * @param p 通信結果の保育士の一覧
         */
        void onPartiesFetched ( List<Party> p );
    }
}
