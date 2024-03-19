package com.blog_api.blog_api.service.implementation;

import com.blog_api.blog_api.entity.Comment;
import com.blog_api.blog_api.entity.Post;
import com.blog_api.blog_api.exception.CommentException;
import com.blog_api.blog_api.exception.PostException;
import com.blog_api.blog_api.exception.PostNotFoundException;
import com.blog_api.blog_api.repository.PostRepository;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Log4j
class PostServiceImplementationTest {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImplementation postService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreatePost_Success() {
        Post post = Post.builder().id(1L).title("New post").content("Welcome to my blog post").authorId(2L).build();
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post createdPost = postService.createPost(post);
        assertNotNull(createdPost);
        assertEquals(post.getTitle(), createdPost.getTitle());


    }

    @Test
    public void testCreatePost_Failure() {
        Post post = Post.builder().id(2L).title("Second post").content("Welcome to my second blog post").build();
        when(postRepository.save(any(Post.class))).thenThrow(RuntimeException.class);

        assertThrows(PostException.class, () -> postService.createPost(post));
    }

    @Test
    public void testGetAllPosts_Success() {
        Post postOne = Post.builder().id(1L).title("First post").content("Welcome to my blog post").build();

        Post postTwo = Post.builder().id(1L).title("Second post").content("Welcome to my blog post").build();
        List<Post> posts = Arrays.asList(postOne, postTwo);
        when(postRepository.findAll()).thenReturn(posts);

        List<Post> fetchedPosts = postService.getAllPosts();

        assertNotNull(fetchedPosts);
        assertEquals(posts.size(), fetchedPosts.size());
    }

    @Test
    public void testGetAllPosts_Failure() {
        when(postRepository.findAll()).thenThrow(RuntimeException.class);

        assertThrows(PostException.class, () -> postService.getAllPosts());
    }


    @Test
    public void testFindById_Success() {
        Long postId = 1L;
        Post post = Post.builder().id(postId).title("test post").content("Welcome to my blog post").build();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Post fetchedPost = postService.findById(postId);

        assertNotNull(fetchedPost);
        assertEquals(post.getTitle(), fetchedPost.getTitle());
        assertEquals(post.getContent(), fetchedPost.getContent());
    }

    @Test
    public void testFindById_PostNotFound() {
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.findById(postId));
    }

    @Test
    void deletePost() {
        Long postId = 1L;
        Post post = Post.builder().id(postId).title("test post").content("Welcome to my blog post").build();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        doNothing().when(postRepository).deleteById(postId);

        assertDoesNotThrow(() -> postService.deletePost(postId));
    }


    @Test
    void testDelete_postNotFound() {
        Long postId = 1L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());
        PostException exception = assertThrows(PostException.class, () -> postService.deletePost(postId));

        assertEquals("Failed to delete post with ID " + postId, exception.getMessage());

        verify(postRepository).findById(postId);
        verifyNoMoreInteractions(postRepository);
    }

    @Test
    public void testEditComment_Success() {
        Long postId = 1L;

        Post originalPost = Post.builder().id(postId).title("test post").content("original post").build();
        Post updatedPost = Post.builder().id(postId).title("test post").content("Updated post").build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(originalPost));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        Post editedPost = postService.editPost(postId, updatedPost);

        assertNotNull(editedPost);
        assertEquals(updatedPost.getContent(), editedPost.getContent());
    }

    @Test
    public void testEditPost_PostNotFound() {
        Long postId = 1L;
        Post updatedPost = Post.builder().id(postId).title("test post").content("Updated post").build();

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(PostException.class, () -> postService.editPost(postId, updatedPost));
    }
}