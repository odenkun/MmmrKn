package com.example.android.mmmrkn.presentation.party;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.android.mmmrkn.R;

//属性と親情報をRecyclerViewから持ってくる
public class PartyCardRecyclerView extends RecyclerView {
    public PartyCardRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRecyclerAdapter(context);
    }
    /*valuesのstringsから都道府県の取得
      Adapterのセット*/
    public void setRecyclerAdapter(Context context){
        setLayoutManager(new LinearLayoutManager(context));
        Resources resources = context.getResources();
        String[] dummies = resources.getStringArray(R.array.dummy);
        PartyCardRecyclerAdapter cra = new PartyCardRecyclerAdapter(context, dummies);
        this.setAdapter(cra);
    }
}