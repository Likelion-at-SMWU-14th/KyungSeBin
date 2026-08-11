package com.likelion.seminar.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @JoinColumn(name="board_id")
    @ManyToOne(fetch=FetchType.LAZY)
    private Board board;

    public Post(String title, String content, Board board){
        this.title=title;
        this.content=content;
        this.board=board;
    }
}
