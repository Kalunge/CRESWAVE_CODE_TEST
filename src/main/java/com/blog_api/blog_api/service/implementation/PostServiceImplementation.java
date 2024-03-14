package com.blog_api.blog_api.service.implementation;

import com.blog_api.blog_api.entity.Post;
import com.blog_api.blog_api.exception.PostException;
import com.blog_api.blog_api.repository.PostRepository;
import com.blog_api.blog_api.service.PostService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Log4j
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;


    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post findById(Long postId) {
//        log.info("Get the Post for postId: " + postId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostException("Post with the given id could not be found", "POST_NOT_FOUND"));
        return post;
    }

    @Override
    public void deletePost(Long postId) {
        Post postToDelete = postRepository.findById(postId).orElseThrow(() -> new PostException("Post does not exist with the given Id", "NOT_FOUND"));
        postRepository.delete(postToDelete);
    }

    @Override
    public Post editPost(Long postId, Post post) {
        Post postToEdit = postRepository.findById(postId).orElseThrow(() -> new PostException("Post does not exist with the given Id", "NOT_FOUND"));
        postToEdit.setContent(post.getContent());
        postToEdit.setTitle(postToEdit.getTitle());

        return postRepository.save(postToEdit);
    }
}
