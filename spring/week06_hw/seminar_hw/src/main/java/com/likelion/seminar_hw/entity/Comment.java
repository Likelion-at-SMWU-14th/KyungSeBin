package com.likelion.seminar_hw.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    Comment(String content, Author author, Board board) {
        this.content = content;
        this.author = Objects.requireNonNull(author, "작성자는 필수입니다.");
        this.board = Objects.requireNonNull(board, "게시글은 필수입니다.");

        author.addCommentInternal(this);
    }

    void detachFromBoard() {
        this.board = null;
        this.author.removeCommentInternal(this);
    }

    public void updateContent(String content) {
        this.content = content;
    }
}