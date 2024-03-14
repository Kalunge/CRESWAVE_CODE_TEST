package com.blog_api.blog_api.service.implementation;

import com.blog_api.blog_api.entity.Post;
import com.blog_api.blog_api.repository.PostRepository;
import com.blog_api.blog_api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
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
}
