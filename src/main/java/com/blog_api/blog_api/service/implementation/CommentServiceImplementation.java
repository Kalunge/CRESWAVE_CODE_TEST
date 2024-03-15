package com.blog_api.blog_api.service.implementation;

import com.blog_api.blog_api.entity.Comment;
import com.blog_api.blog_api.entity.Post;
import com.blog_api.blog_api.exception.CommentException;
import com.blog_api.blog_api.exception.PostException;
import com.blog_api.blog_api.repository.CommentRepository;
import com.blog_api.blog_api.repository.PostRepository;
import com.blog_api.blog_api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImplementation implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImplementation(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Comment createComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostException("No post found with the given ID", "NOT_FOUND"));

        Comment commentToSave = Comment.builder()
                .content(comment.getContent())
                .postId(post.getId())
                .userId(comment.getUserId())
                .build();

        return commentRepository.save(commentToSave);
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
