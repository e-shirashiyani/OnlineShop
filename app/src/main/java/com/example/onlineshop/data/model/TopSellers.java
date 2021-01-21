package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

public class TopSellers {
    @SerializedName("product_id")
    private int mId;

    @SerializedName("quantity")
    private int mQuantity;
    @SerializedName("title")
    private String mTitle;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public TopSellers(int id, String title,int quantity) {
        mId = id;
        mTitle = title;
        mQuantity = quantity;
    }
}
