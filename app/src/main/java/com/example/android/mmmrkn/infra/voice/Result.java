package com.example.android.mmmrkn.infra.voice;


import java.util.Arrays;

public class Result {
    public enum Type {
        PROVISIONAL,
        FINAL,
        TIME,
        FAMILY,
        UNSPECIFIED
    }
    public String type;
    public String[] content;

    @Override
    public String toString () {
        return "Result{" +
                "type='" + type + '\'' +
                ", content=" + Arrays.toString ( content ) +
                '}';
    }

    public Type getEnumType () {
        return Type.valueOf ( this.type.toUpperCase () );
    }
}
