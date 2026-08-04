package com.likelion.seminar.service;

import com.likelion.seminar.dto.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private List<Product> products=new ArrayList<>();
    public Product addProduct(Product product){
        products.add(product);
        return product;
    }

    public List<Product> findAll(){ return products;}
}
