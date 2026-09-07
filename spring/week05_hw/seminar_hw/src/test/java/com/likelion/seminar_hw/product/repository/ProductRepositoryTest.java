package com.likelion.seminar_hw.product.repository;

import com.likelion.seminar_hw.product.domain.Product;
import com.likelion.seminar_hw.product.domain.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        // 테스트 전용 DB의 데이터를 정리
        productRepository.deleteAllInBatch();

        // 정렬이 실제로 수행되는지 확인-> 역순저장
        for (int i = 12; i >= 1; i--) {
            productRepository.save(
                    new Product(
                            "볼펜%02d".formatted(i),
                            i * 100,
                            i * 10
                    )
            );
        }

        productRepository.save(new Product("노트", 2000, 500));
        productRepository.save(new Product("고급노트", 2001, 999));
        productRepository.save(new Product("지우개", 50, 1));

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("JPA: 가격이 높은 상품을 최대 10개 조회")
    void findTop10ExpensiveProducts() {
        // when
        List<Product> result =
                productRepository.findTop10ByOrderByPriceDescIdAsc();

        // then
        assertThat(result).hasSize(10);

        assertThat(result)
                .extracting(Product::getPrice)
                .containsExactly(
                        2001, 2000, 1200, 1100, 1000,
                        900, 800, 700, 600, 500
                );
    }

    @Test
    @DisplayName("JPQL: 2000원 이하 상품 중 재고가 많은 5개를 조회")
    void findTop5AffordableProductsWithHighStock() {
        // when
        List<Product> result =
                productRepository.findAffordableProducts(
                        2000,
                        PageRequest.of(0, 5)
                );

        // then
        assertThat(result).hasSize(5);

        assertThat(result)
                .allSatisfy(product ->
                        assertThat(product.getPrice())
                                .isLessThanOrEqualTo(2000)
                );

        assertThat(result)
                .extracting(Product::getName)
                .containsExactly(
                        "노트",
                        "볼펜12",
                        "볼펜11",
                        "볼펜10",
                        "볼펜09"
                );

        assertThat(result)
                .extracting(Product::getStock)
                .containsExactly(500, 120, 110, 100, 90);
    }

    @Test
    @DisplayName("QueryDSL: 이름에 펜이 포함된 상품 중 저렴한 10개를 조회")
    void findTop10CheapPens() {
        // given
        JPAQueryFactory queryFactory =
                new JPAQueryFactory(entityManager);

        QProduct product = QProduct.product;

        // when: 과제에서 요구하는 QueryDSL을 @Test 안에 작성
        List<Product> result = queryFactory
                .selectFrom(product)
                .where(product.name.contains("펜"))
                .orderBy(product.price.asc(), product.id.asc())
                .limit(10)
                .fetch();

        // then
        assertThat(result).hasSize(10);

        assertThat(result)
                .allSatisfy(item ->
                        assertThat(item.getName()).contains("펜")
                );

        assertThat(result)
                .extracting(Product::getPrice)
                .containsExactly(
                        100, 200, 300, 400, 500,
                        600, 700, 800, 900, 1000
                );
    }
}