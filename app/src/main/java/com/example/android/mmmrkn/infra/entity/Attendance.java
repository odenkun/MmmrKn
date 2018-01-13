package com.example.android.mmmrkn.infra.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Attendance implements Serializable{

    public enum Condition {
        GOOD("good"),
        SUBTLE("subtle"),
        BAD("bad");
        Condition(String name) {
            this.name = name;
        }
        private String name;

        @Override
        public String toString () {
            return name;
        }
    }


//    @PrimaryKey(auto = false)
//    @SerializedName("attendanceId")
//    @Expose
//    private String attendanceId;

    @SerializedName("time")
    @Expose
    private String time;
    
    @SerializedName("condition")
    @Expose
    private String condition;
    
    @SerializedName("detail")
    @Expose
    private String detail;

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
        for ( Condition c : Condition.values () ) {
            if (c.toString ().equals ( condition )) {
                this.condition = condition;
                return;
            }
        }
        throw new RuntimeException ( "Illegal condition" );

    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
