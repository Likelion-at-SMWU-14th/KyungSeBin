package com.likelion.seminar_hw.product.dto;

import com.likelion.seminar_hw.product.domain.Product;

public record ProductResponse(
        Long id,
        String name,
        int price,
        int stock
) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }
}