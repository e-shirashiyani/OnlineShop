package com.example.onlineshop.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.onlineshop.data.model.Cart;
import com.example.onlineshop.data.model.MapAddress;

import java.util.List;
@Dao
public interface CartDatabaseDAO {
    @Update
    void updateCart(Cart cart);

    @Insert
    void insertCart(Cart cart);

    @Insert
    void insertCarts(List<Cart> carts);

    @Delete
    void deleteCart(Cart cart);

    @Query("DELETE FROM cart")
    void deleteAllCart();

    @Query("SELECT * FROM cart")
    List<Cart> getCarts();

    @Query("SELECT * FROM cart WHERE product_id=:productId")
    Cart getCart(int productId);


    @Update
    void updateAddress(MapAddress mapAddress);

    @Insert
    void insertAddress(MapAddress mapAddress);

    @Insert
    void insertAddresses(List<MapAddress> mapAddresses);

    @Delete
    void deleteAddress(MapAddress mapAddress);

    @Query("SELECT * FROM address")
    List<MapAddress> getAddresses();

    @Query("SELECT * FROM address WHERE selected_address=1")
    MapAddress getAddress();

    @Query("SELECT * FROM address WHERE primary_id=:addressId")
    MapAddress getAddressWithId(long addressId);

}
