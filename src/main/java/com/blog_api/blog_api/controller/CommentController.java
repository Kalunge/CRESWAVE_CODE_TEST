package com.blog_api.blog_api.controller;

import com.blog_api.blog_api.dto.CommentNotFoundResponse;
import com.blog_api.blog_api.entity.Comment;
import com.blog_api.blog_api.exception.CommentException;
import com.blog_api.blog_api.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@PathVariable("postId") Long postId, @RequestBody Comment comment) {
        try {
            Comment createComment = commentService.createComment(postId, comment);
            return new ResponseEntity<>(createComment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllPosts(@PathVariable Long postId) {
        try {
            List<Comment> comments = commentService.getAllComments(postId);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable("postId") long postId, @PathVariable("id") long commentId) {
        try {
            Optional<Comment> comment = commentService.findById(postId, commentId);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (CommentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommentNotFoundResponse("Comment not found", postId, commentId));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("postId") long postId, @PathVariable("id") long commentId) {
        try {
            commentService.deleteComment(postId, commentId);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CommentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommentNotFoundResponse("Comment not found", postId, commentId));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editComment(@PathVariable("postId") long postId, @PathVariable("id") long commentId, @RequestBody Comment comment) {
        try {
            Comment updateComment = commentService.editComment(postId, commentId, comment);
            return new ResponseEntity<>(updateComment, HttpStatus.OK);
        } catch (CommentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommentNotFoundResponse("Comment not found", postId, commentId));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
