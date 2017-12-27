package com.example.android.mmmrkn.infra.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Teacher implements Serializable{
    private static final long serialVersionUID = 1L;

    @SerializedName("teacherId")
    @Expose
    private String teacherId;

    @SerializedName ("name")
    @Expose
    private String name;

    public Teacher () {
    }

    public Teacher ( String teacherId, String name ) {
        this.teacherId = teacherId;
        this.name = name;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString () {
        return "Teacher{" +
                "teacherId='" + teacherId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}