package com.example.webpos.biz;

import com.example.webpos.db.PosDB;
import com.example.webpos.model.Cart;
import com.example.webpos.model.Item;
import com.example.webpos.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PosServiceImp implements PosService {

    private PosDB posDB;

    @Autowired
    public void setPosDB(PosDB posDB) {
        this.posDB = posDB;
    }

    @Override
    public Cart getCart() {

        Cart cart = posDB.getCart();
        if (cart == null){
            cart = this.newCart(); // 保证会话开始时一定有Cart
        }
        return cart;
    }

    @Override
    public Cart newCart() {
        return posDB.saveCart(new Cart());
    }

    @Override
    public void checkout(Cart cart) {

    }

    @Override
    public double total(Cart cart) {
        if(cart==null){
            return Double.MIN_VALUE;
        }
        double sumOfTips = 0.0;
        for(Item item:cart.getItems()){
            sumOfTips += item.getProduct().getPrice() * item.getQuantity();

        }
        return sumOfTips;
    }

    @Override
    public boolean add(Product product, int amount) {
        return false;
    }

    @Override
    public boolean add(String productId, int amount) {

        Product product = posDB.getProduct(productId);
        if (product == null) return false; // 不存在该product

        // 会话中Cart不为空
        if(this.getCart() == null){
            System.err.println("Error: Cart is null");
            return false;
        }
        int i=0;
        for(;i<this.getCart().getItems().size();i++){
            if(productId.equals(this.getCart().getItems().get(i).getProduct().getId())){
                break;
            }
        }
        if(i==this.getCart().getItems().size()){
            this.getCart().addItem(new Item(product, amount));
        }else{
            int number = this.getCart().getItems().get(i).getQuantity();
            if(number+amount>=0){
                this.getCart().getItems().set(i,new Item(product, number+amount));
            }else{//小于0
                return false;
            }
        }

        return true;
    }

    @Override
    public List<Product> products() {
        return posDB.getProducts();
    }

    @Override
    public boolean clearCart(){
        if(this.getCart() == null){
            return false;
        }
        this.getCart().getItems().clear();
        return true;
    }
    @Override
    public boolean trash(String pid){
        List<Item> list = this.getCart().getItems();
        Cart cart = new Cart();
        for(int i =0;i<list.size();i++){
            if(pid.equals(list.get(i).getProduct().getId())){
                continue;
            }else{
                cart.addItem(list.get(i));
            }
        }
        posDB.saveCart(cart);
        return true;

    }
    @Override
    public Set<String> categories(){
        return posDB.categories();
    }

    @Override
    public List<Product> getProductOfCategory(String category){
        List<Product> list = new ArrayList<>();
        for(Product product:posDB.getProducts()){
            if(category.equals(product.getCategory())){
                list.add(product);
            }
        }
        return list;
    }
}
