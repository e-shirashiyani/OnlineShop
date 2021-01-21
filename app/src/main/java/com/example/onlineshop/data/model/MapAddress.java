package com.example.onlineshop.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;


@Entity(tableName = "address")
public class MapAddress {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "primary_id")
    private long primaryId;

    @ColumnInfo(name = "address_name")
    private String addressName;

    @ColumnInfo(name = "address_lat")
    private double address_lat;

    @ColumnInfo(name = "address_lng")
    private double address_lng;

    @ColumnInfo(name = "selected_address")
    private int selected_address;

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public double getAddress_lat() {
        return address_lat;
    }

    public void setAddress_lat(double address_lat) {
        this.address_lat = address_lat;
    }

    public double getAddress_lng() {
        return address_lng;
    }

    public void setAddress_lng(double address_lng) {
        this.address_lng = address_lng;
    }

    public int getSelected_address() {
        return selected_address;
    }

    public void setSelected_address(int selected_address) {
        this.selected_address = selected_address;
    }

    public MapAddress(String addressName, double address_lat, double address_lng, int selected_address) {
        this.addressName = addressName;
        this.address_lat = address_lat;
        this.address_lng = address_lng;
        this.selected_address = selected_address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapAddress that = (MapAddress) o;
        return primaryId == that.primaryId &&
                Double.compare(that.address_lat, address_lat) == 0 &&
                Double.compare(that.address_lng, address_lng) == 0 &&
                selected_address == that.selected_address &&
                Objects.equals(addressName, that.addressName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryId, addressName, address_lat, address_lng, selected_address);
    }
}
