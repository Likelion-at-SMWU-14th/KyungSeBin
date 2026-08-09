package com.likelion.seminar_hw.controller;

import com.likelion.seminar_hw.model.Product;
import com.likelion.seminar_hw.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){this.productService=productService;}

    @GetMapping("/products")
    public String viewProducts (Model page){
        var products=productService.findAll();
        page.addAttribute("products",products);

        return "products";
    }

    @PostMapping("/products")
    public String addProduct(Product product, Model page){
        productService.addProduct(product);

        var products=productService.findAll();
        page.addAttribute("products",products);

        return "products";
    }
}
