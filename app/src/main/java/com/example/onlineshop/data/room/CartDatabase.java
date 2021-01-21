package com.example.onlineshop.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.onlineshop.data.model.Cart;
import com.example.onlineshop.data.model.MapAddress;

@Database(entities = {Cart.class, MapAddress.class}, version = 1)
public abstract class CartDatabase extends RoomDatabase {

    public abstract CartDatabaseDAO getCartDatabaseDAO();
}
