package com.example.onlineshop.data.repository;


import com.example.onlineshop.data.model.MapAddress;

import java.util.List;

public interface IAddressRepository {

    void updateAddress(MapAddress mapAddress);
    void insertAddress(MapAddress mapAddress);
    void insertAddresses(List<MapAddress> mapAddresses);
    void deleteAddress(MapAddress mapAddress);
    List<MapAddress> getMapAddresses();
    MapAddress getAddress();
    MapAddress getAddressWithId(long addressId);
}
