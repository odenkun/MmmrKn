package com.example.android.mmmrkn.di.party;

import com.example.android.mmmrkn.presentation.party.SelectPartyPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PartyModule {
    private SelectPartyPresenter.Contract contract;
    public PartyModule(SelectPartyPresenter.Contract contract){
        this.contract = contract;
    }
    @Provides
    public SelectPartyPresenter.Contract provideContract(){return contract;}
}
