package com.likelion.seminar_hw.controller;

import com.likelion.seminar_hw.dto.Product;
import com.likelion.seminar_hw.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<Product> getProducts(){
        return productService.findAll();
    }

    @PostMapping("/products")
   public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }
}
