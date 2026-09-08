package com.likelion.seminar;

import com.likelion.seminar.entity.Category;
import com.likelion.seminar.entity.Product;
import com.likelion.seminar.entity.Provider;
import com.likelion.seminar.repository.CategoryRepository;
import com.likelion.seminar.repository.ProductRepository;
import com.likelion.seminar.repository.ProviderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProductMappingTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void MappingTest() {

        // 1. Provider 생성 및 저장
        Provider provider = new Provider("Apple Korea");
        providerRepository.save(provider);

        // 2. Category 생성 및 저장
        Category category = new Category("전자제품");
        categoryRepository.save(category);

        // 3. Product 생성
        Product product1 = new Product("MacBook", 2000000);
        Product product2 = new Product("iPhone", 1500000);

        // 4. Product에 Provider와 Category 연결
        product1.setProvider(provider);
        product1.setCategory(category);

        product2.setProvider(provider);
        product2.setCategory(category);


        // 5. Product 저장
        productRepository.save(product1);
        productRepository.save(product2);


        // 6. DB 반영 후 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();

        // Product → Provider (N:1)
        Product findProduct = productRepository.findById(product1.getNumber())
                .orElseThrow();

        assertThat(findProduct.getProvider().getName())
                .isEqualTo("Apple Korea");


        // Product → Category (N:1)
        assertThat(findProduct.getCategory().getName())
                .isEqualTo("전자제품");


        // Provider → Product (1:N)
        Provider findProvider = providerRepository.findById(provider.getId())
                .orElseThrow();

        assertThat(findProvider.getProducts())
                .hasSize(2);

        assertThat(findProvider.getProducts())
                .extracting(Product::getName)
                .containsExactlyInAnyOrder(
                        "MacBook",
                        "iPhone"
                );

        // Category → Product (1:N)
        Category findCategory =
                categoryRepository.findById(category.getId())
                        .orElseThrow();

        assertThat(findCategory.getProducts())
                .hasSize(2);

        assertThat(findCategory.getProducts())
                .extracting(Product::getName)
                .containsExactlyInAnyOrder(
                        "MacBook", "iPhone"
                );
    }
}