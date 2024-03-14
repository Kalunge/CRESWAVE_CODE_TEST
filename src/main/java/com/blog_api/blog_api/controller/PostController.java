package com.blog_api.blog_api.controller;

import com.blog_api.blog_api.entity.Post;
import com.blog_api.blog_api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;


    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createPost = postService.save(post);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") long postId) {
        Post post = postService.findById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePost(@PathVariable("id") long postId) {
        postService.deletePost(postId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> editPost(@PathVariable("id") long postId, @RequestBody Post post) {
        Post updatePost = postService.editPost(postId, post);

        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }
}
