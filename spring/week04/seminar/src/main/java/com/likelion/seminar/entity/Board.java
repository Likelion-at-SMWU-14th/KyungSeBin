package com.likelion.seminar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name="board")
public class Board {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(
            fetch=FetchType.LAZY,
            mappedBy="board"
    )
    private List<Post> posts=new ArrayList<>();
    private Board(String name){
        this.name=name;
    }
}
