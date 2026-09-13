package com.likelion.seminar_hw.entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Entity
@Table(name = "authors")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Author extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "author")
    private List<Board> boards = new ArrayList<>();

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "author")
    private List<Comment> comments = new ArrayList<>();

    public Author(String name) {
        this.name = name;
    }

    public List<Board> getBoards() {
        return Collections.unmodifiableList(boards);
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    void addBoardInternal(Board board) {
        boards.add(board);
    }

    void addCommentInternal(Comment comment) {
        comments.add(comment);
    }

    void removeCommentInternal(Comment comment) {
        comments.remove(comment);
    }
}