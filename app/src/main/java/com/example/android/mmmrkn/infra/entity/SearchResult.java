package com.example.android.mmmrkn.infra.entity;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SearchResult {
    
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
