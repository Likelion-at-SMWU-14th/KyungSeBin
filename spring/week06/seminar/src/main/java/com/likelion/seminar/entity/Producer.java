package com.likelion.seminar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Producer extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy="producers")
    @ToString.Exclude
    private List<Product> products=new ArrayList<>();

    public Producer (String name){
        this.name=name;
    }
}
