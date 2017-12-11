package com.example.android.mmmrkn.infra.entity;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Table
public class AttendancesLog {
    
    @PrimaryKey(auto = false)
    @SerializedName("attendanceId")
    @Expose
    private String attendanceId;
    
    @Column
    @SerializedName("time")
    @Expose
    private String time;
    
    @SerializedName("condition")
    @Expose
    private String condition;
    
    @SerializedName("detail")
    @Expose
    private String detail;
    
    @SerializedName("teacherId")
    @Expose
    private String teacherId;

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

}
