package com.example.android.mmmrkn.presentation.party;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.di.party.PartyModule;
import com.example.android.mmmrkn.di.teacher.TeacherModule;
import com.example.android.mmmrkn.infra.entity.Party;
import com.example.android.mmmrkn.presentation.App;
import com.example.android.mmmrkn.presentation.login.LoginPresenter;

import java.security.spec.PSSParameterSpec;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by 15110009 on 2017/11/27.
 */

public class SelectPartyActivity extends AppCompatActivity  implements SelectPartyPresenter.Contract{

    @Inject
    SelectPartyPresenter presenter;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_party_recycler_base );

        ( (App) getApplication () )
                .getComponent()
                .plus(new PartyModule(this) )
                .inject(this);
        presenter.fetchParties();
    }

    //OrmaからデータをListに代入
    @Override
    public  void onPartiesFetched(List<Party> partyList){
        PartyCardRecyclerView cardRecyclerView = (PartyCardRecyclerView)findViewById(R.id.party_recycle);

       cardRecyclerView.onListFetch(this, partyList);
        //データとしてlog出力
        for (Party party : partyList){
            Timber.d(party.toString());
        }
    }
    @Override
    protected void onDestroy () {
        //通信の結果を受け取らなくする
        presenter.dispose ();
        super.onDestroy ();
    }
}
