package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

public class Coupons {
    @SerializedName("id")
    private int mId;
    @SerializedName("code")
    private String mCode;
    @SerializedName("amount")
    private String mAmount;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public Coupons(int id, String code, String amount) {
        mId = id;
        mCode = code;
        mAmount = amount;
    }
}
