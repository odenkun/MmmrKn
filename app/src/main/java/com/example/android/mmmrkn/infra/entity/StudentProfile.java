package com.example.android.mmmrkn.infra.entity;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Table
public class StudentProfile {
    
    @PrimaryKey(auto = false)
    @SerializedName("studentId")
    @Expose
    private String studentId;
    
    @Column
    @SerializedName("name")
    @Expose
    private String name;
    
    @SerializedName("nickname")
    @Expose
    private String nickname;
    
    @SerializedName("picturePath")
    @Expose
    private String picturePath;
    
    @SerializedName("gender")
    @Expose
    private String gender;
    
    @SerializedName("birthDay")
    @Expose
    private String birthDay;
    
    @SerializedName("party")
    @Expose
    private Party party;
    
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
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
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
    
    public String getBirthDay() {
        return birthDay;
    }
    
    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }
    
    public Party getParty() {
        return party;
    }
    
    public void setParty(Party party) {
        this.party = party;
    }
    
    @Override
    public String toString() {
        return "StudentProfile{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", picturePath='" + picturePath + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", party=" + party +
                '}';
    }
}



