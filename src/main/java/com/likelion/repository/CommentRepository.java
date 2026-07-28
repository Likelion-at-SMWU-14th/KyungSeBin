package com.likelion.repository;

import com.likelion.model.Comment;

public interface CommentRepository {

    void storeComment(Comment comment);
}
