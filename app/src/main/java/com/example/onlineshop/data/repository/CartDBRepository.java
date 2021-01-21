package com.example.onlineshop.data.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.onlineshop.data.model.Cart;
import com.example.onlineshop.data.room.CartDatabase;
import com.example.onlineshop.data.room.CartDatabaseDAO;

import java.util.List;

public class CartDBRepository implements IRepository {
    private static CartDBRepository sInstance;

    private CartDatabaseDAO mCartDAO;
    private Context mContext;
    private List<Cart> mCarts;

    public static CartDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new CartDBRepository(context);

        return sInstance;
    }

    private CartDBRepository(Context context) {
        mContext = context.getApplicationContext();
        CartDatabase cartDatabase = Room.databaseBuilder(mContext,
                CartDatabase.class,
                "cart.db")
                .allowMainThreadQueries()
                .build();

        mCartDAO = cartDatabase.getCartDatabaseDAO();
    }

    @Override
    public void updateCart(Cart cart) {
        mCartDAO.updateCart(cart);
    }

    @Override
    public void insertCart(Cart cart) {
        mCartDAO.insertCart(cart);
    }

    @Override
    public void insertCarts(List<Cart> carts) {
        mCartDAO.insertCarts(carts);
    }

    @Override
    public void deleteCart(Cart cart) {
        mCartDAO.deleteCart(cart);
    }

    @Override
    public void deleteAllCart() {
        mCartDAO.deleteAllCart();
    }

    @Override
    public List<Cart> getCarts() {
        return mCartDAO.getCarts();
    }

    @Override
    public Cart getCart(int productId) {
        return mCartDAO.getCart(productId);
    }
}
