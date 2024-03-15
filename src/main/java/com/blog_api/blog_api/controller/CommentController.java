package com.blog_api.blog_api.controller;

import com.blog_api.blog_api.entity.Comment;
import com.blog_api.blog_api.entity.Post;
import com.blog_api.blog_api.exception.CommentException;
import com.blog_api.blog_api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment createComment = commentService.createComment(comment);
        return new ResponseEntity<>(createComment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllPosts() {
        List<Comment> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getPostById(@PathVariable("id") long commentId) {
        Comment comment = commentService.findById(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteComment(@PathVariable("id") long commentId) {
        commentService.deleteComment(commentId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> editComment(@PathVariable("id") long commentId, @RequestBody Comment comment) {
        try {
            Comment updateComment = commentService.editComment(commentId, comment);
            return new ResponseEntity<>(updateComment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
