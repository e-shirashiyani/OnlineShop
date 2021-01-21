package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

public class ProductCategory {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("parent")
    private int mParent;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("image")
    private String mImage;
    @SerializedName("count")
    private int mCount;

    public ProductCategory(int id, String name, int parent, String description, String image, int count) {
        mId = id;
        mName = name;
        mParent = parent;
        mDescription = description;
        mImage = image;
        mCount = count;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getParent() {
        return mParent;
    }

    public void setParent(int parent) {
        mParent = parent;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public int getCount() {
        return mCount;
    }
}
