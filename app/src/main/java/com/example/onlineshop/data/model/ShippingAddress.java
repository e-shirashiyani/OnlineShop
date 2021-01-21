package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

public class ShippingAddress {
    @SerializedName("first_name")
    private String mFirst_name;
    @SerializedName("last_name")
    private String  mLast_name;
    @SerializedName("company")
    private String mCompany;
    @SerializedName("address_1")
    private String mAddress_1;
    @SerializedName("address_2")
    private String  mAddress_2;
    @SerializedName("city")
    private String  mCity;
    @SerializedName("state")
    private String   mState;
    @SerializedName("postcode")
    private String  mPostcode;
    @SerializedName("country")
    private String   mCountry;

    public ShippingAddress(String first_name, String last_name, String company, String address_1,
                           String address_2, String city, String  state, String postcode, String  country) {
        mFirst_name = first_name;
        mLast_name = last_name;
        mCompany = company;
        mAddress_1 = address_1;
        mAddress_2 = address_2;
        mCity = city;
        mState = state;
        mPostcode = postcode;
        mCountry = country;
    }

    public String getFirst_name() {
        return mFirst_name;
    }

    public void setFirst_name(String first_name) {
        mFirst_name = first_name;
    }

    public String getLast_name() {
        return mLast_name;
    }

    public void setLast_name(String last_name) {
        mLast_name = last_name;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String company) {
        mCompany = company;
    }

    public String getAddress_1() {
        return mAddress_1;
    }

    public void setAddress_1(String address_1) {
        mAddress_1 = address_1;
    }

    public String getAddress_2() {
        return mAddress_2;
    }

    public void setAddress_2(String address_2) {
        mAddress_2 = address_2;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getState() {
        return mState;
    }

    public void setState(String  state) {
        mState = state;
    }

    public String getPostcode() {
        return mPostcode;
    }

    public void setPostcode(String postcode) {
        mPostcode = postcode;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

}
