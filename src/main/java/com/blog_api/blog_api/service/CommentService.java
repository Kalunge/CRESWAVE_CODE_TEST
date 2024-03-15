package com.blog_api.blog_api.service;

import com.blog_api.blog_api.dto.CommentDto;
import com.blog_api.blog_api.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment createComment(Long postId, Comment comment);

    List<Comment> getAllComments(Long postId);


    Optional<Comment> findById(Long  postId, Long commentId);

    void deleteComment(Long postId,Long commentId);

    Comment editComment(Long postId, Long commentId, Comment comment);
}
