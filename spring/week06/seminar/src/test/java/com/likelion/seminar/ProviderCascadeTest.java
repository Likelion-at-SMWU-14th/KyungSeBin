package com.likelion.seminar;

import com.likelion.seminar.entity.Product;
import com.likelion.seminar.entity.Provider;
import com.likelion.seminar.repository.ProviderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Transactional
class ProviderCascadeTest {

    @Autowired
    private ProviderRepository providerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void cascadePersistTest() {

        // 1. Provider 생성
        Provider provider = new Provider("Apple Korea");

        // 2. Product 생성
        Product product1 = new Product("MacBook", 2000000);

        Product product2 = new Product("iPhone", 1500000);

        // 3. 연관관계 설정
        provider.addProduct(product1);
        provider.addProduct(product2);

        // 4. Provider만 저장
        providerRepository.save(provider);

        // Product는 직접 save하지 않음!
        // productRepository.save(product1);
        // productRepository.save(product2);

        // 5. DB 반영 후 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();

        // 6. Product가 같이 저장되었는지 확인
        Provider findProvider = providerRepository.findById(provider.getId())
                .orElseThrow();

        assertThat(findProvider.getProducts())
                .hasSize(2);

        assertThat(findProvider.getProducts())
                .extracting(Product::getName)
                .containsExactlyInAnyOrder(
                        "MacBook", "iPhone"
                );
    }
}
