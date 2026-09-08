package com.likelion.seminar;

import com.likelion.seminar.entity.Product;
import com.likelion.seminar.entity.ProductDetail;
import com.likelion.seminar.repository.ProductDetailRepository;
import com.likelion.seminar.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductDetailTest {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Test
    void saveAndReadTest() {

        Product product = new Product("Sample Product", 10000);
        // product 값 설정

        // 1. Product 먼저 저장
        productRepository.save(product);

        ProductDetail productDetail = new ProductDetail();

        // productDetail 값 설정
        productDetail.setProduct(product);
        productDetail.setDescription("Sample Product Description");

        // 2. ProductDetail 저장
        productDetailRepository.save(productDetail);

        // 3. ProductDetail → Product 조회
        ProductDetail savedDetail = productDetailRepository.findById(productDetail.getId())
                .orElseThrow();

        Product savedProduct = savedDetail.getProduct();

        System.out.println("Saved Product: " + savedProduct.getName());
        System.out.println("Saved Product Description: " + savedDetail.getDescription());

        // 4. Product → ProductDetail 조회
        Product findProduct = productRepository
                .findById(product.getNumber())
                .orElseThrow();

        ProductDetail findProductDetail = findProduct.getProductDetail();

        System.out.println("Product → ProductDetail: " + findProductDetail.getDescription()
        );
    }
}
