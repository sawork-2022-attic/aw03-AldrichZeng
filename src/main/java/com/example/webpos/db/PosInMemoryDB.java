package com.example.webpos.db;

import com.example.webpos.model.Cart;
import com.example.webpos.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PosInMemoryDB implements PosDB {
    private List<Product> products = new ArrayList<>();

    private Cart cart;

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Cart saveCart(Cart cart) {
        this.cart = cart;
        return this.cart;
    }

    @Override
    public Cart getCart() {
        return this.cart;
    }

    @Override
    public Product getProduct(String productId) {
        for (Product p : getProducts()) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    private PosInMemoryDB() {
        // 已有的一些数据
        this.products.add(new Product("PD1", "Iron", 8999, "1.jpg","home"));
        this.products.add(new Product("PD2", "DianFanBao", 29499, "2.jpg","home"));
        this.products.add(new Product("PD3", "Bag", 29499, "3.jpg","female"));
        this.products.add(new Product("PD4", "Phone", 29499, "4.jpg","eletric"));
        this.products.add(new Product("PD5", "Sofa", 29499, "5.jpg","relax"));
        this.products.add(new Product("PD6", "Desk", 29499, "6.jpg","relax"));
        this.products.add(new Product("PD7", "Watch", 29499, "7.jpg","eletric"));
        this.products.add(new Product("PD8", "Monitor", 29499, "comp.png","eletric"));

    }

    public Set<String> categories(){
        Set<String> set = new HashSet<>();
        for(Product product:this.products){
            set.add(product.getCategory());
        }
        return set;
    }

}
