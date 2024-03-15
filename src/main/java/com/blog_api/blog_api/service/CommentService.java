package com.blog_api.blog_api.service;

import com.blog_api.blog_api.entity.Comment;
import com.blog_api.blog_api.entity.Post;

import java.util.List;

public interface CommentService {
    Comment createComment(Long postId, Comment comment);

    List<Comment> getAllComments(Long postId);


    Comment findById(Long commentId);

    void deleteComment(Long commentId);

    Comment editComment(Long commentId, Comment comment);
}
