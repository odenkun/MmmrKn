package com.example.android.mmmrkn.infra.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Party implements Serializable {

    @SerializedName("partyId")
    @Expose
    private String partyId;

    @SerializedName("name")
    @Expose
    private String name;

    public String getPartyId() { return partyId; }

    public void setPartyId(String partyId) { this.partyId = partyId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Party{" +
                "partyId='" + partyId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
