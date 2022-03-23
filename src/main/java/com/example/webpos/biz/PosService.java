package com.example.webpos.biz;

import com.example.webpos.model.Cart;
import com.example.webpos.model.Product;

import java.util.List;
import java.util.Set;

public interface PosService {
    public Cart getCart();

    public Cart newCart();

    public void checkout(Cart cart);

    public double total(Cart cart);

    public boolean add(Product product, int amount);

    public boolean add(String productId, int amount);


    public List<Product> products();

    public boolean clearCart();

    public boolean trash(String pid);

    public Set<String> categories();

    public List<Product> getProductOfCategory(String category);
}
