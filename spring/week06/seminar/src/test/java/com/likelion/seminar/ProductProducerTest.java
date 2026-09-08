package com.likelion.seminar;

import com.likelion.seminar.entity.Producer;
import com.likelion.seminar.entity.Product;
import com.likelion.seminar.repository.ProducerRepository;
import com.likelion.seminar.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProductProducerTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void manyToManyTest() {

        // 1. Producer 생성
        Producer producer1 = new Producer("생산업체 A");
        Producer producer2 = new Producer("생산업체 B");

        // Cascade 설정이 없으므로 Producer 먼저 저장
        producerRepository.save(producer1);
        producerRepository.save(producer2);


        // 2. Product 생성
        Product product1 = new Product("MacBook", 2000000);
        Product product2 = new Product("iPhone", 1500000);

        // 3. Product → Producer 관계 설정
        product1.getProducers().add(producer1);
        product1.getProducers().add(producer2);

        product2.getProducers().add(producer1);


        // 4. 연관관계의 주인인 Product 저장
        productRepository.save(product1);
        productRepository.save(product2);


        // 5. DB 반영 후 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();

        // Product → Producer
        Product findProduct = productRepository.findById(product1.getNumber())
                .orElseThrow();

        assertThat(findProduct.getProducers())
                .hasSize(2);

        assertThat(findProduct.getProducers())
                .extracting(Producer::getName)
                .containsExactlyInAnyOrder(
                        "생산업체 A", "생산업체 B"
                );

        // Producer → Product
        Producer findProducer = producerRepository.findById(producer1.getId())
                .orElseThrow();

        assertThat(findProducer.getProducts())
                .hasSize(2);

        assertThat(findProducer.getProducts())
                .extracting(Product::getName)
                .containsExactlyInAnyOrder(
                        "MacBook", "iPhone"
                );
    }
}
