package com.blog_api.blog_api.service.implementation;

import com.blog_api.blog_api.dto.CommentDto;
import com.blog_api.blog_api.entity.Comment;
import com.blog_api.blog_api.entity.Post;
import com.blog_api.blog_api.entity.User;
import com.blog_api.blog_api.exception.CommentException;
import com.blog_api.blog_api.exception.CommentNotFoundException;
import com.blog_api.blog_api.exception.PostException;
import com.blog_api.blog_api.repository.CommentRepository;
import com.blog_api.blog_api.repository.PostRepository;
import com.blog_api.blog_api.repository.UserRepository;
import com.blog_api.blog_api.service.CommentService;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j
public class CommentServiceImplementation implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImplementation(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Comment createComment(Long postId, Comment comment) {
        try {
            log.info("Creating comment, with params: " + comment);
            Post post = postRepository.findById(postId).orElseThrow(() -> new PostException("No post found with the given ID", "NOT_FOUND"));
            Comment commentToSave = Comment.builder().content(comment.getContent()).postId(post.getId()).userId(comment.getUserId()).build();
            return commentRepository.save(commentToSave);
        } catch (PostException e) {
            throw new PostException("No post found with the given ID", "NOT_FOUND");
        } catch (Exception e) {
            log.error("Failed to create comment " + e.getMessage());
            throw new CommentException("Failed to create comment", e.getMessage());
        }
    }

    @Override
    public List<Comment> getAllComments(Long postId) {
        try {
            return commentRepository.findByPostId(postId);
        } catch (Exception e) {
            log.error("Failed to retrieve comments for post with ID: " + postId + e.getMessage());
            throw new CommentException("Failed to retrieve comments", e.getMessage());
        }

    }

    @Override
    public Optional<Comment> findById(Long postId, Long commentId) {
        try {
            List<Comment> comments = commentRepository.findByPostId(postId);
            return Optional.of(comments.stream().filter(comm -> comm.getId().equals(commentId)).findFirst().get());
        } catch (CommentNotFoundException e) {
            throw new CommentException("No Comment found with the given ID", "NOT_FOUND");
        } catch (Exception e) {
            log.error("Failed to find comment " + e.getMessage());
            throw new CommentException("Failed to find comment", e.getMessage());
        }
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        try {
            List<Comment> comments = commentRepository.findByPostId(postId);
            Optional<Comment> optionalComment = Optional.of(comments.stream().filter(comm -> comm.getId().equals(commentId)).findFirst().get());
            commentRepository.deleteById(optionalComment.get().getId());
        } catch (CommentNotFoundException e) {
            throw new CommentException("No Comment found with the given ID", "NOT_FOUND");
        } catch (EmptyResultDataAccessException e) {
            throw new CommentNotFoundException("Comment not found with ID: " + commentId);
        } catch (Exception e) {
            log.error("Failed to delete comment " + e.getMessage());
            throw new CommentException("Failed to delete comment", e.getMessage());
        }
    }

    @Override
    public Comment editComment(Long postId, Long commentId, Comment comment) {
        try {
            List<Comment> comments = commentRepository.findByPostId(postId);
            Optional<Comment> optionalComment = Optional.of(comments.stream().filter(comm -> comm.getId().equals(commentId)).findFirst().get());
            Comment editedComment = optionalComment.get();
            editedComment.setContent(comment.getContent());
            return commentRepository.save(editedComment);
        } catch (CommentNotFoundException e) {
            throw new CommentNotFoundException("Comment not found with ID: " + commentId);
        } catch (Exception e) {
            log.error("Failed to edit comment " + e.getMessage());
            throw new CommentException("Failed to edit comment", e.getMessage());
        }
    }
}
