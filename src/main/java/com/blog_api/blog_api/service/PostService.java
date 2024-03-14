package com.blog_api.blog_api.service;

import com.blog_api.blog_api.entity.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post);

    List<Post> getAllPosts();
    Post save(Post post);

    Post findById(Long postId);

    void deletePost(Long postId);

    Post editPost(Long postId, Post post);

}
