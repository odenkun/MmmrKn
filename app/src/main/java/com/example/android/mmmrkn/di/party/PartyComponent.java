package com.example.android.mmmrkn.di.party;

import com.example.android.mmmrkn.presentation.party.SelectPartyActivity;

import dagger.Subcomponent;

/**
 * Created by 15110009 on 2017/11/29.
 */
@Subcomponent(modules = {PartyModule.class})
public interface PartyComponent {
    void inject(SelectPartyActivity s);
}
