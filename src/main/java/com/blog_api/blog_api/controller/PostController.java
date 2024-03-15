package com.blog_api.blog_api.controller;

import com.blog_api.blog_api.dto.PostNotFoundResponse;
import com.blog_api.blog_api.entity.Post;
import com.blog_api.blog_api.exception.PostException;
import com.blog_api.blog_api.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        try {
            Post createPost = postService.save(post);
            return new ResponseEntity<>(createPost, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        try {
            List<Post> posts = postService.getAllPosts();
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable("id") long postId) {
        try {
            Post post = postService.findById(postId);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (PostException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PostNotFoundResponse("Post not found", postId));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") long postId) {
        try {
            postService.deletePost(postId);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (PostException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PostNotFoundResponse("Post not found", postId));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editPost(@PathVariable("id") long postId, @RequestBody Post post) {
        try {
            Post updatePost = postService.editPost(postId, post);
            return new ResponseEntity<>(updatePost, HttpStatus.OK);
        } catch (PostException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PostNotFoundResponse("Post not found", postId));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
