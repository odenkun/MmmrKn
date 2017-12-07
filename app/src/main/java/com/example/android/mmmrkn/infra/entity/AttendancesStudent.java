package com.example.android.mmmrkn.infra.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by 15110012 on 2017/12/04.
 */

public class AttendancesStudent extends BaseObservable {
    private String studentId;

    public AttendancesStudent(String studentId){
        this.studentId=studentId;
    }

    @Bindable
    public String getStudentId(){
        return studentId;
    }

    public void setStudentId(String studentId){
        this.studentId=studentId;

        notifyPropertyChanged(BR.studentId);
    }
}