package com.likelion.proxy;

import com.likelion.model.Comment;

public interface CommentNotificationProxy {

    void sendComment(Comment comment);
}
