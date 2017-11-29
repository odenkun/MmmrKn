package com.example.android.mmmrkn.presentation.party;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.infra.entity.Party;

import java.util.List;

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

    public void onListFetch(Context context, List<Party> parties){
        PartyCardRecyclerAdapter cra = new PartyCardRecyclerAdapter(context,parties);
        this.setAdapter(cra);
    }
}