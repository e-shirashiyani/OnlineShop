package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

public class SalesReport {
    @SerializedName("total_items")
    private int mTotalItems;

    public int getTotalItems() {
        return mTotalItems;
    }

    public void setTotalItems(int totalItems) {
        mTotalItems = totalItems;
    }

    public SalesReport(int totalItems) {
        mTotalItems = totalItems;
    }
}