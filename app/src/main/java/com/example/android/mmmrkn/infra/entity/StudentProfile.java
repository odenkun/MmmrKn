package com.example.android.mmmrkn.infra.entity;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 15110016 on 2017/11/20.
 */
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

    @Column
    @SerializedName("nickname")
    @Expose
    private String nickname;

    @Column
    @SerializedName("picturePath")
    @Expose
    private String picturePath;

    @Column
    @SerializedName("gender")
    @Expose
    private String gender;

    @Column
    @SerializedName("partyId")
    @Expose
    private String partyId;

    @Column
    @SerializedName("birthDay")
    @Expose
    private String birthDay;

    public String getStudentId() { return studentId; }

    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getNickname() { return nickname;}

    public void setNickname(String nickname) { this.nickname = nickname;}

    public String getPicturePath() { return picturePath; }

    public void setPicturePath(String picturePath) { this.picturePath = picturePath; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getPartyId() { return partyId; }

    public void setPartyId(String partyId) { this.partyId = partyId; }

    public String getBirthDay() { return birthDay; }

    public void setBirthDay(String birthDay) { this.birthDay = birthDay; }

    @Override
    public String toString() {
        return "StudentProfileActivity{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", picturePath='" + picturePath + '\'' +
                ", gender='" + gender + '\'' +
                ", partyId='" + partyId + '\'' +
                ", birthDay='" + birthDay + '\'' +
                '}';
    }
}

