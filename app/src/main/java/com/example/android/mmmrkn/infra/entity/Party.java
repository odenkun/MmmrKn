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
public class Party {

    @PrimaryKey (auto = false)
    @SerializedName("partyId")
    @Expose
    private String partyId;

    @Column
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
