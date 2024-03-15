package com.blog_api.blog_api.service.implementation;

import com.blog_api.blog_api.entity.Comment;
import com.blog_api.blog_api.exception.CommentException;
import com.blog_api.blog_api.repository.CommentRepository;
import com.blog_api.blog_api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImplementation implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }


    @Override
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CommentException("Comment does not exist with the given Id", "NOT_FOUND"));
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment editedComment = commentRepository.findById(commentId).orElseThrow(() -> new CommentException("Comment does not exist with the given Id", "NOT_FOUND"));
        commentRepository.delete(editedComment);
    }

    @Override
    public Comment editComment(Long commentId, Comment comment) {
        Comment editedComment = commentRepository.findById(commentId).orElseThrow(() -> new CommentException("Comment does not exist with the given Id", "NOT_FOUND"));
        editedComment.setContent(comment.getContent());
        return commentRepository.save(editedComment);

    }
}
