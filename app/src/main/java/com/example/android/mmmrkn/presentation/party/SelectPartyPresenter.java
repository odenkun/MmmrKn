package com.example.android.mmmrkn.presentation.party;

import com.example.android.mmmrkn.infra.api.PartiesService;
import com.example.android.mmmrkn.presentation.Presenter;
import com.example.android.mmmrkn.presentation.login.LoginPresenter;

import javax.inject.Inject;

/**
 * Created by 15110009 on 2017/11/27.
 */

public class SelectPartyPresenter  extends Presenter {

    private Contract contract;

    private PartiesService partiesService;

    @Inject
    public SelectPartyPresenter(Contract contract, PartiesService partiesService){
        this.contract = contract;
        this.partiesService = partiesService;
    }

    public interface Contract {
        void onAuthStart();
        //
    }
}
