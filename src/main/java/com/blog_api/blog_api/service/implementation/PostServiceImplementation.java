package com.blog_api.blog_api.service.implementation;

import com.blog_api.blog_api.entity.Post;
import com.blog_api.blog_api.exception.PostException;
import com.blog_api.blog_api.exception.PostNotFoundException;
import com.blog_api.blog_api.repository.PostRepository;
import com.blog_api.blog_api.service.PostService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
public class PostServiceImplementation implements PostService {

    private final PostRepository postRepository;

    public PostServiceImplementation(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(Post post) {
        try {
            return postRepository.save(post);
        } catch (Exception e) {
            log.error("Failed to create post: " + e.getMessage());
            throw new PostException("Failed to create post", e.getMessage());
        }
    }

    @Override
    public List<Post> getAllPosts() {
        try {
            return postRepository.findAll();
        } catch (Exception e) {
            log.error("Failed to retrieve all posts: " + e.getMessage());
            throw new PostException("Failed to retrieve all posts", e.getMessage());
        }
    }

    @Override
    public Post save(Post post) {
        try {
            return postRepository.save(post);
        } catch (Exception e) {
            log.error("Failed to save post: " + e.getMessage());
            throw new PostException("Failed to save post", e.getMessage());
        }
    }

    @Override
    public Post findById(Long postId) {
        try {
            log.info("Getting the post for postId: " + postId);
            return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post with the given ID could not be found", "POST_NOT_FOUND"));
        } catch (PostNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to find post with ID: " + postId + e.getMessage());
            throw new PostException("Failed to find post with ID " + postId, e.getMessage());
        }
    }

    @Override
    public void deletePost(Long postId) {
        try {
            Post postToDelete = postRepository.findById(postId).orElseThrow(() -> new PostException("Post does not exist with the given ID", "NOT_FOUND"));
            postRepository.delete(postToDelete);
        } catch (Exception e) {
            log.error("Failed to delete post with ID ID: " + postId + e.getMessage());
            throw new PostException("Failed to delete post with ID " + postId, e.getMessage());
        }
    }

    @Override
    public Post editPost(Long postId, Post post) {
        try {
            Post postToEdit = postRepository.findById(postId).orElseThrow(() -> new PostException("Post does not exist with the given ID", "NOT_FOUND"));
            postToEdit.setContent(post.getContent());
            postToEdit.setTitle(post.getTitle());
            return postRepository.save(postToEdit);
        } catch (Exception e) {
            log.error("Failed to edit post with ID: " + postId + " " + e.getMessage());
            throw new PostException("Failed to edit post with ID " + postId, e.getMessage());
        }
    }
}
