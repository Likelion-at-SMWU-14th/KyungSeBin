package com.likelion.seminar_hw.product.repository;

import com.likelion.seminar_hw.product.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 1번: 가격이 높은 상품부터 최대 10개 나열하기
    List<Product> findTop10ByOrderByPriceDescIdAsc();

    // 2번: 지정 가격 이하인 상품을 재고가 많은 순서로 조회하기
    @Query("""
            select p
            from Product p
            where p.price <= :maxPrice
            order by p.stock desc, p.id asc
            """)
    List<Product> findAffordableProducts(
            @Param("maxPrice") int maxPrice,
            Pageable pageable
    );
}