package com.blog_api.blog_api.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.blog_api.blog_api.dto.PostNotFoundResponse;
import com.blog_api.blog_api.entity.Post;
import com.blog_api.blog_api.exception.ForbiddenException;
import com.blog_api.blog_api.exception.PostException;
import com.blog_api.blog_api.exception.PostNotFoundException;
import com.blog_api.blog_api.exception.UnauthorizedException;
import com.blog_api.blog_api.service.PostService;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
@Log4j
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        if (!userIsAuthenticated()) {
            throw new UnauthorizedException("You must be logged in");
        }

        try {
            Post createPost = postService.save(post);
            return new ResponseEntity<>(createPost, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
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
        } catch (PostNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PostNotFoundResponse("Post not found", postId));
        } catch (Exception e) {
            log.error("in general exception block");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") long postId) {

        if (userHasPermission()) {
            throw new ForbiddenException("You do not have permission to access this resource, you must be an admin");
        }
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
        if (!userHasPermission()) {
            throw new ForbiddenException("You do not have permission to access this resource, you must be an admin");
        }
        try {
            Post updatePost = postService.editPost(postId, post);
            return new ResponseEntity<>(updatePost, HttpStatus.OK);
        } catch (PostException e) {
            log.error(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PostNotFoundResponse("Post not found", postId));
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Post>> searchAndSortPosts(
            @RequestParam String keyword,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false, defaultValue = "title") String sortBy,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sortBy);
        Page<Post> searchResults = postService.searchAndSortPosts(keyword, pageable);
        return ResponseEntity.ok(searchResults);
    }


    private boolean userHasPermission() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null || authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }

    private boolean userIsAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ||
                authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")) ||
                authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));
    }

}
