package com.likelion.seminar_hw.product.service;

import com.likelion.seminar_hw.product.dto.ProductResponse;
import com.likelion.seminar_hw.product.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getExpensiveProducts() {
        return productRepository.findTop10ByOrderByPriceDescIdAsc()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    public List<ProductResponse> getAffordableProductsWithHighStock() {
        return productRepository.findAffordableProducts(
                        2000,
                        PageRequest.of(0, 5)
                )
                .stream()
                .map(ProductResponse::from)
                .toList();
    }
}