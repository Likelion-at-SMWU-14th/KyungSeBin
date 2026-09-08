package com.likelion.seminar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category extends BaseEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    //카테고리 이름
    private String name;

    @OneToMany(mappedBy="category")
    private List<Product> products=new ArrayList<>();

    public Category(String name){
        this.name=name;
    }
}
