package com.example.android.mmmrkn.presentation.cardview_gohome_dialog;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.android.mmmrkn.R;

/**
 * Created by 15110012 on 2017/11/21.
 */

public class CardRecyclerView extends RecyclerView {
    public CardRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRecyclerAdapter(context);
    }
    //valuesのstringsから都道府県の取得
    public void setRecyclerAdapter(Context context){
        setLayoutManager(new LinearLayoutManager(context));
        Resources resources = context.getResources();
        String[] dummies = resources.getStringArray(R.array.dummy);
        CardRecyclerAdapter cra = new CardRecyclerAdapter(context, dummies);
        this.setAdapter(cra);
    }
}