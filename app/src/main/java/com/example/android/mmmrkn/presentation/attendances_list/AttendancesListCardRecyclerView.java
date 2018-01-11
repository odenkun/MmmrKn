package com.example.android.mmmrkn.presentation.attendances_list;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.android.mmmrkn.infra.entity.Party;
import com.example.android.mmmrkn.infra.entity.Student;

import java.util.List;

/**
 * Created by 15110009 on 2017/12/01.
 */

public class AttendancesListCardRecyclerView extends RecyclerView{

    public AttendancesListCardRecyclerView(Context context, AttributeSet attrs){
        super(context, attrs);
        setRecyclerAdapter(context);
    }
    public  void setRecyclerAdapter(Context context){
        setLayoutManager(new LinearLayoutManager(context));
    }

    public  void onStudentListFetch(Context context, List<Student> studentTList, Party party){
        AttendancesListCardRecyclerAdapter cra = new AttendancesListCardRecyclerAdapter(context, studentTList, party);
        this.setAdapter(cra);
    }
}