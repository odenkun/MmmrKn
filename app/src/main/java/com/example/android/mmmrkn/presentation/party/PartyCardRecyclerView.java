package com.example.android.mmmrkn.presentation.party;

import android.support.v4.app.DialogFragment;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.infra.entity.Party;
import com.example.android.mmmrkn.presentation.attendances_list.AttendancesDialog;

import java.util.List;

import javax.inject.Inject;

//属性と親情報をRecyclerViewから持ってくる
public class PartyCardRecyclerView extends RecyclerView {



    public PartyCardRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRecyclerAdapter(context);
    }
    /*setレイアウト*/
    public void setRecyclerAdapter(Context context){
        setLayoutManager(new LinearLayoutManager(context));
    }

    public void onPartyListFetch(AttendancesDialog dialogFragment, List<Party> parties){
        PartyCardRecyclerAdapter cra = new PartyCardRecyclerAdapter(dialogFragment,parties);
        this.setAdapter(cra);
    }
}