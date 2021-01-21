package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Attributes {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("position")
    private int mPosition;
    @SerializedName("options")
    private List<String> mOptions;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public List<String> getOptions() {
        return mOptions;
    }

    public void setOptions(List<String> options) {
        mOptions = options;
    }

    public Attributes(int id, String name, int position, List<String> options) {
        mId = id;
        mName = name;
        mPosition = position;
        mOptions = options;
    }
}
