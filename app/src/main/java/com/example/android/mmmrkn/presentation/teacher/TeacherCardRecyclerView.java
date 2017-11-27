package com.example.android.mmmrkn.presentation.teacher;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.android.mmmrkn.infra.entity.Teacher;

import java.util.List;

//CardRecyclerAdapterに値渡し
public class TeacherCardRecyclerView extends RecyclerView {

    public TeacherCardRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRecyclerAdapter(context);

    }
    /*setレイアウト*/
    public void setRecyclerAdapter(Context context){
        setLayoutManager(new LinearLayoutManager(context));
    }
    //CardRecyclerAdapter呼び出し
    public void onListFetch(Context context,List<Teacher> teachers){
        TeacherCardRecyclerAdapter cra = new TeacherCardRecyclerAdapter(context,teachers);
        this.setAdapter(cra);
    }
}