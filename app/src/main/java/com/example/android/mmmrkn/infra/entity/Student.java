package com.example.android.mmmrkn.infra.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.reactivex.annotations.Nullable;


public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    @SerializedName("studentId")
    @Expose
    private String studentId;
    
    @SerializedName("name")
    @Expose
    private String name;
    
    @SerializedName("picturePath")
    @Expose
    private String picturePath;
    
    @SerializedName("gender")
    @Expose
    private String gender;
    
    @SerializedName("party")
    @Expose
    private Party party;
    
    @Nullable
    @SerializedName("attendance")
    @Expose
    private Attendance attendance;
    
    @Nullable
    @SerializedName("goHome")
    @Expose
    private GoHome gohome;
    
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPicturePath() {
        return picturePath;
    }
    
    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public Party getParty() {
        return party;
    }
    
    public void setParty(Party party) {
        this.party = party;
    }
    
    public Attendance getAttendance() {
        return attendance;
    }
    
    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }
    
    public GoHome getGohome() {
        return gohome;
    }
    
    public void setGohome(GoHome gohome) {
        this.gohome = gohome;
    }
}


