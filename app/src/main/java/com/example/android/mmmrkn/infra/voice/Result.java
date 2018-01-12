package com.example.android.mmmrkn.infra.voice;

enum Type {
    PROVISIONAL,
    FINAL,
    TIME,
    FAMILY,
    UNSPECIFIED
}

public class Result {
    public String type;
    public String content;
    @Override
    public String toString () {
        return "Result{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Type getEnumType () {
        return Type.valueOf ( this.type.toUpperCase () );
    }
}
