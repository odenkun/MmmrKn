package com.example.android.mmmrkn.presentation.student_search;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.android.mmmrkn.R;

//属性と親情報をRecyclerViewから持ってくる
public class CardRecyclerView extends RecyclerView{
    public CardRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRecyclerAdapter(context);
    }
    /*valuesのstringsから都道府県の取得
      Adapterのセット*/
    public void setRecyclerAdapter(Context context){
        setLayoutManager(new LinearLayoutManager(context));
        Resources resources = context.getResources();
        String[] dummies = resources.getStringArray(R.array.dummy);
        CardRecyclerAdapter cra = new CardRecyclerAdapter(context, dummies);
        this.setAdapter(cra);
    }
}