package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id")
    private int mId;
    @SerializedName("product_id")
    private int mProduct_id;
    @SerializedName("date_created_gmt")
    private String mDate_created_gmt;
    @SerializedName("reviewer")
    private String mReviewer;
    @SerializedName("reviewer_email")
    private String  mReviewer_email;
    @SerializedName("review")
    private String  mReview;
    @SerializedName("rating")
    private int mRating;
    @SerializedName("verified")
    private boolean mVerified;

    public int getProduct_id() {
        return mProduct_id;
    }

    public void setProduct_id(int product_id) {
        mProduct_id = product_id;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getDate_created_gmt() {
        return mDate_created_gmt;
    }

    public void setDate_created_gmt(String date_created_gmt) {
        mDate_created_gmt = date_created_gmt;
    }

    public String getReviewer() {
        return mReviewer;
    }

    public void setReviewer(String reviewer) {
        mReviewer = reviewer;
    }

    public String getReviewer_email() {
        return mReviewer_email;
    }

    public void setReviewer_email(String reviewer_email) {
        mReviewer_email = reviewer_email;
    }

    public String getReview() {
        return mReview;
    }

    public void setReview(String review) {
        mReview = review;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public boolean isVerified() {
        return mVerified;
    }

    public void setVerified(boolean verified) {
        mVerified = verified;
    }

    public Comment(int id, int product_id, String date_created_gmt, String reviewer,
                   String reviewer_email, String review, int rating, boolean verified) {
        mId = id;
        mProduct_id = product_id;
        mDate_created_gmt = date_created_gmt;
        mReviewer = reviewer;
        mReviewer_email = reviewer_email;
        mReview = review;
        mRating = rating;
        mVerified = verified;
    }
}
