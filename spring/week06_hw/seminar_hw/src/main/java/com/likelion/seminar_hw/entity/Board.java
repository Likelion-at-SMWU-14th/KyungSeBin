package com.likelion.seminar_hw.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@Table(name = "boards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Getter(AccessLevel.NONE)
    @OneToMany(
            mappedBy = "board",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    public Board(String title, String content, Author author) {
        this.title = title;
        this.content = content;
        this.author = Objects.requireNonNull(author, "작성자는 필수입니다.");

        author.addBoardInternal(this);
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public Comment addComment(String content, Author author) {
        Comment comment = new Comment(content, author, this);
        comments.add(comment);
        return comment;
    }

    public void removeComment(Comment comment) {
        if (comments.remove(comment)) {
            comment.detachFromBoard();
        }
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}