package com.example.webpos.web;

import com.example.webpos.biz.PosService;
import com.example.webpos.model.Item;
import com.example.webpos.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class PosController {

    private PosService posService;

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @GetMapping("/")
    public String pos(Model model) {
        // 获取所有的product
        model.addAttribute("products", posService.products());
        // 初始化一个购物车
        model.addAttribute("cart",posService.getCart());
        // 初始化分类标签
        model.addAttribute("categories",posService.categories());

//        model.addAttribute("categories",posService.categories());

//        posService.add("PD1",2);
//        model.addAttribute("products", posService.products());
//        model.addAttribute("cart", posService.getCart());
//        return "index";
        return "index";
    }

    @GetMapping("/add")
    public String add(@RequestParam(name = "pid") String pid, Model model){
        posService.add(pid, 1);
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", posService.getCart());
        model.addAttribute("categories",posService.categories());
        return "index";
    }

    @GetMapping("/plus")
    public String plus(@RequestParam(name="pid") String pid,Model model){
        posService.add(pid, 1);
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", posService.getCart());
        model.addAttribute("categories",posService.categories());
        return "index";
    }

    @GetMapping("/minus")
    public String minus(@RequestParam(name="pid") String pid,Model model){
        posService.add(pid, -1);
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", posService.getCart());
        model.addAttribute("categories",posService.categories());
        return "index";
    }
    @GetMapping("/cancel")
    public String cancel(Model model){
        posService.clearCart();
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", posService.getCart());
        model.addAttribute("categories",posService.categories());
        return "index";
    }
    @GetMapping("/charge")
    public String charge(Model model){
        double sumOfTips = posService.total(posService.getCart());
        if(sumOfTips>=0){
            model.addAttribute("total",sumOfTips);
        }
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", posService.getCart());
        model.addAttribute("categories",posService.categories());
        return "index";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam(name="pid") String productId, Model model){
        posService.trash(productId);
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", posService.getCart());
        model.addAttribute("categories",posService.categories());
        return "index";
    }
    @GetMapping("/forcategory")
    public String category(@RequestParam(name="cat") String cat, Model model){
        List<Product> list = posService.getProductOfCategory(cat);
        System.out.println(list.toString());
        model.addAttribute("products", list);
        model.addAttribute("cart", posService.getCart());
        model.addAttribute("categories",posService.categories());
        return "index";
    }




}
