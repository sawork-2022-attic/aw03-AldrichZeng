package com.example.webpos.db;

import com.example.webpos.model.Cart;
import com.example.webpos.model.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface PosDB {

    public List<Product> getProducts();

    public Cart saveCart(Cart cart);

    public Cart getCart();

    public Product getProduct(String productId);

    public Set<String> categories();

}
