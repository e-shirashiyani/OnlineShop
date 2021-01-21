package com.example.onlineshop.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "cart")
public class Cart {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cart_id")
    private long primaryId;

    @ColumnInfo(name = "user_id")
    private long userId;

    @ColumnInfo(name = "product_id")
    private int product_id;

    @ColumnInfo(name = "product_count")
    private int product_count;

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_count() {
        return product_count;
    }

    public void setProduct_count(int product_count) {
        this.product_count = product_count;
    }

    public Cart(int product_id, int product_count) {
        this.product_id = product_id;
        this.product_count = product_count;
    }
}
