package com.likelion.seminar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long number;

    //상품 이름
    private String name;
    //상품 가격
    private Integer price;
    //상품 재고
    private Integer stock;

    public Product(String name, Integer price){
        this.name=name;
        this.price=price;
    }

    @OneToOne(mappedBy="product")
    @ToString.Exclude
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name="provider_id")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToMany
    @ToString.Exclude
    private List<Producer> producers=new ArrayList<>();
}
