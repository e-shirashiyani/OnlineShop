package com.example.onlineshop.data.repository;

import com.example.onlineshop.data.model.Cart;

import java.util.List;

public interface IRepository {
    void updateCart(Cart cart);
    void insertCart(Cart cart);
    void insertCarts(List<Cart> carts);
    void deleteCart(Cart cart);
    void deleteAllCart();
    List<Cart> getCarts();
    Cart getCart(int productId);
}
