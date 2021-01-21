package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("title")
    private String mTitle;
    @SerializedName("id")
    private int mId;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("regular_price")
    private String mRegular_price;
    @SerializedName("sale_price")
    private String  mSale_price;
    @SerializedName("sale_price_dates_from")
    private String  mSale_price_dates_from;
    @SerializedName("sale_price_dates_to")
    private String  mSale_price_dates_to;
    @SerializedName("weight")
    private String  mWeight;
    @SerializedName("length")
    private String  mLength;
    @SerializedName("width")
    private String  mWidth;
    @SerializedName("height")
    private String  mHeight;
    @SerializedName("description")
    private String  mDescription;
    @SerializedName("short_description")
    private String  mShort_description;
    @SerializedName("average_rating")
    private String  mAverage_rating;
    @SerializedName("rating_count")
    private int mRating_count;
    @SerializedName("total_sales")
    private int mTotal_sales;
    @SerializedName("images")
    private List<Images> mImages;
    @SerializedName("attributes")
    private List<Attributes> mAttributes;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getId() {
        return mId;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getRegular_price() {
        return mRegular_price;
    }

    public void setRegular_price(String regular_price) {
        mRegular_price = regular_price;
    }

    public String getSale_price() {
        return mSale_price;
    }

    public void setSale_price(String sale_price) {
        mSale_price = sale_price;
    }

    public String getSale_price_dates_from() {
        return mSale_price_dates_from;
    }

    public void setSale_price_dates_from(String sale_price_dates_from) {
        mSale_price_dates_from = sale_price_dates_from;
    }

    public String getSale_price_dates_to() {
        return mSale_price_dates_to;
    }

    public void setSale_price_dates_to(String sale_price_dates_to) {
        mSale_price_dates_to = sale_price_dates_to;
    }

    public String getWeight() {
        return mWeight;
    }

    public void setWeight(String weight) {
        mWeight = weight;
    }

    public String getLength() {
        return mLength;
    }

    public void setLength(String length) {
        mLength = length;
    }

    public String getWidth() {
        return mWidth;
    }

    public void setWidth(String width) {
        mWidth = width;
    }

    public String getHeight() {
        return mHeight;
    }

    public void setHeight(String height) {
        mHeight = height;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getShort_description() {
        return mShort_description;
    }

    public void setShort_description(String short_description) {
        mShort_description = short_description;
    }

    public String getAverage_rating() {
        return mAverage_rating;
    }

    public int getRating_count() {
        return mRating_count;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public void setAverage_rating(String average_rating) {
        mAverage_rating = average_rating;
    }

    public void setRating_count(int rating_count) {
        mRating_count = rating_count;
    }

    public int getTotal_sales() {
        return mTotal_sales;
    }

    public void setTotal_sales(int total_sales) {
        mTotal_sales = total_sales;
    }

    public List<Images> getImages() {
        return mImages;
    }

    public void setImages(List<Images> images) {
        mImages = images;
    }

    public List<Attributes> getAttributes() {
        return mAttributes;
    }

    public void setAttributes(List<Attributes> attributes) {
        mAttributes = attributes;
    }

    public Product() {
    }

    public Product(String title, int id, String price, String regular_price, String sale_price
            , String weight, String length, String width, String height, String description,
                   String short_description, String average_rating, int rating_count, int total_sales,
                   List<Images> images,List<Attributes> attributes) {
        mTitle = title;
        mId = id;
        mPrice = price;
        mRegular_price = regular_price;
        mSale_price = sale_price;
        mWeight = weight;
        mLength = length;
        mWidth = width;
        mHeight = height;
        mDescription = description;
        mShort_description = short_description;
        mAverage_rating = average_rating;
        mRating_count = rating_count;
        mTotal_sales = total_sales;
        mImages = images;
        mAttributes = attributes;
    }
}
