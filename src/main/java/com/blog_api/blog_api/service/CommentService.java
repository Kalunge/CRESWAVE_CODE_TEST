package com.blog_api.blog_api.service;

import com.blog_api.blog_api.entity.Comment;
import com.blog_api.blog_api.entity.Post;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment);

    List<Comment> getAllComments();


    Comment findById(Long commentId);

    void deleteComment(Long commentId);

    Comment editComment(Long commentId, Comment comment);
}
