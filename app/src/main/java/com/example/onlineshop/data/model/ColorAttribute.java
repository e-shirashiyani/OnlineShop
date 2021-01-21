package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

public class ColorAttribute {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private String  mDescription;
    @SerializedName("count")
    private int mCount;

    public ColorAttribute(int id, String name, String description, int count) {
        mId = id;
        mName = name;
        mDescription = description;
        mCount = count;
    }

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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }
}
