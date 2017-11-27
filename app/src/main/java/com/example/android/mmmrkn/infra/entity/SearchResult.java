package com.example.android.mmmrkn.infra.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 15110016 on 2017/11/20.
 */

public class SearchResult {@SerializedName("studentId")
@Expose
private String studentId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("picturePath")
    @Expose
    private String picturePath;

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

}
