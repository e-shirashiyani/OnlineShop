package com.example.onlineshop.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Customer {
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("created_at")
    @Expose
    private String mCreated_at;
    @SerializedName("email")
    @Expose
    private String mEmail;
    @SerializedName("first_name")
    @Expose
    private String mFirst_name;
    @SerializedName("last_name")
    @Expose
    private String  mLast_name;
    @SerializedName("username")
    @Expose
    private String  mUsername;
    @SerializedName("password")
    @Expose
    private String  mPassword;
    @SerializedName("last_order_id")
    @Expose
    private int  mLast_order_id;
    @SerializedName("last_order_date")
    @Expose
    private String  mLast_order_date;
    @SerializedName("orders_count")
    @Expose
    private int  mOrders_count;
    @SerializedName("total_spent")
    @Expose
    private int  mTotal_spent;
    @SerializedName("avatar_url")
    @Expose
    private String  mAvatar_url;
    @SerializedName("billing_address")
    @Expose
    private BillingAddress[]  mBilling_address;
    @SerializedName("shipping_address")
    @Expose
    private ShippingAddress[]  mShipping_address;

    public Customer(int id, String created_at, String email, String first_name, String last_name,
                    String username, String password, int last_order_id, String last_order_date,
                    int orders_count, int total_spent, String avatar_url,
                    BillingAddress[] billing_address, ShippingAddress[] shipping_address) {
        mId = id;
        mCreated_at = created_at;
        mEmail = email;
        mFirst_name = first_name;
        mLast_name = last_name;
        mUsername = username;
        mPassword = password;
        mLast_order_id = last_order_id;
        mLast_order_date = last_order_date;
        mOrders_count = orders_count;
        mTotal_spent = total_spent;
        mAvatar_url = avatar_url;
        mBilling_address = billing_address;
        mShipping_address = shipping_address;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getCreated_at() {
        return mCreated_at;
    }

    public void setCreated_at(String created_at) {
        mCreated_at = created_at;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
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

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public int getLast_order_id() {
        return mLast_order_id;
    }

    public void setLast_order_id(int last_order_id) {
        mLast_order_id = last_order_id;
    }

    public String getLast_order_date() {
        return mLast_order_date;
    }

    public void setLast_order_date(String last_order_date) {
        mLast_order_date = last_order_date;
    }

    public int getOrders_count() {
        return mOrders_count;
    }

    public void setOrders_count(int orders_count) {
        mOrders_count = orders_count;
    }

    public int getTotal_spent() {
        return mTotal_spent;
    }

    public void setTotal_spent(int total_spent) {
        mTotal_spent = total_spent;
    }

    public String getAvatar_url() {
        return mAvatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        mAvatar_url = avatar_url;
    }

    public BillingAddress[] getBilling_address() {
        return mBilling_address;
    }

    public void setBilling_address(BillingAddress[] billing_address) {
        mBilling_address = billing_address;
    }

    public ShippingAddress[] getShipping_address() {
        return mShipping_address;
    }

    public void setShipping_address(ShippingAddress[] shipping_address) {
        mShipping_address = shipping_address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "mId=" + mId +
                ", mCreated_at='" + mCreated_at + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mFirst_name='" + mFirst_name + '\'' +
                ", mLast_name='" + mLast_name + '\'' +
                ", mUsername='" + mUsername + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mLast_order_id=" + mLast_order_id +
                ", mLast_order_date='" + mLast_order_date + '\'' +
                ", mOrders_count=" + mOrders_count +
                ", mTotal_spent=" + mTotal_spent +
                ", mAvatar_url='" + mAvatar_url + '\'' +
                ", mBilling_address=" + Arrays.toString(mBilling_address) +
                ", mShipping_address=" + Arrays.toString(mShipping_address) +
                '}';
    }
}
