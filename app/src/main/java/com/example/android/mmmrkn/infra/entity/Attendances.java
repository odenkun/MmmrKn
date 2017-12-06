package com.example.android.mmmrkn.infra.entity;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15110016 on 2017/11/20.
 */
@Table
public class Attendances {

    @PrimaryKey(auto = false)
    @SerializedName("studentId")
    @Expose
    private String studentId;

    @Column
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("picturePath")
    @Expose
    private String picturePath;

    @SerializedName("attendances")
    @Expose
    private List<Object> attendances = new ArrayList<Object>();

    @SerializedName("goHomes")
    @Expose
    private List<Object> goHomes = new ArrayList<Object>();

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

    public List<Object> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Object> attendances) {
        this.attendances = attendances;
    }

    public List<Object> getGoHomes() {
        return goHomes;
    }

    public void setGoHomes(List<Object> goHomes) {
        this.goHomes = goHomes;
    }

}
