package com.example.android.mmmrkn.presentation.attendances_list.students;

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
        setLayoutManager(new LinearLayoutManager(context));
        AttendancesListCardRecyclerAdapter cra = new AttendancesListCardRecyclerAdapter(context);
        this.setAdapter(cra);
    }

    public void onStudentListIsEmpty(String party){
        ((AttendancesListCardRecyclerAdapter)getAdapter ()).swap ( null, party);
    }

    public void onStudentListFetch( List<Student> studentList,String party){
        ((AttendancesListCardRecyclerAdapter)getAdapter ()).swap ( studentList, party );
    }
}