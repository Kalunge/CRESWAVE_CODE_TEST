package com.blog_api.blog_api.service.implementation;

import com.blog_api.blog_api.entity.Comment;
import com.blog_api.blog_api.entity.Post;
import com.blog_api.blog_api.exception.CommentException;
import com.blog_api.blog_api.exception.PostException;
import com.blog_api.blog_api.repository.CommentRepository;
import com.blog_api.blog_api.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CommentServiceImplementationTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentServiceImplementation commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testCreateComment_success() {
        Long postId = 1L;
        Comment comment = Comment.builder()
                .id(1L)
                .content("New comment")
                .postId(postId)
                .userId(2L)
                .build();
        when(postRepository.findById(postId)).thenReturn(Optional.of(new Post()));

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment createdComment = commentService.createComment(postId, comment);

        assertNotNull(createdComment);
        assertEquals(comment.getContent(), createdComment.getContent());
    }


    @Test
    public void testCreateComment_PostNotFound() {
        Long postId = 2L;
        Comment comment = Comment.builder()
                .id(1L)
                .content("New comment")
                .postId(postId)
                .userId(2L)
                .build();
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(PostException.class, () -> commentService.createComment(postId, comment));

    }

    @Test
    public void testGetAllComments_Success() {
        Long postId = 1L;

        Comment commentOne = Comment.builder()
                .id(1L)
                .content("New comment")
                .postId(postId)
                .userId(2L)
                .build();

        Comment commentTwo = Comment.builder()
                .id(2L)
                .content("Second comment")
                .postId(postId)
                .userId(2L)
                .build();

        List<Comment> comments = Arrays.asList(commentOne, commentTwo);
        when(commentRepository.findByPostId(postId)).thenReturn(comments);

        List<Comment> fetchedComments = commentService.getAllComments(postId);

        assertNotNull(fetchedComments);
        assertEquals(comments.size(), fetchedComments.size());
    }

    @Test
    public void testGetAllComments_Failure() {
        Long postId = 1L;

        Comment commentTwo = Comment.builder()
                .id(2L)
                .content("Second comment")
                .postId(postId)
                .userId(2L)
                .build();
        when(commentRepository.findByPostId(postId)).thenThrow(RuntimeException.class);

        assertThrows(CommentException.class, () -> commentService.getAllComments(postId));
    }


    @Test
    public void testFindById_Success() {
        Long postId = 1L;
        Long commentId = 1L;
        Comment comment = Comment.builder()
                .id(commentId)
                .content("Third comment")
                .postId(postId)
                .userId(2L)
                .build();
        when(commentRepository.findByPostId(postId)).thenReturn(Arrays.asList(comment));

        Optional<Comment> optionalComment = commentService.findById(postId, commentId);

        assertTrue(optionalComment.isPresent());
        assertEquals(comment.getContent(), optionalComment.get().getContent());
    }

    @Test
    public void testFindById_CommentNotFound() {
        Long postId = 1L;
        Long commentId = 1L;
        when(commentRepository.findByPostId(postId)).thenReturn(Arrays.asList());

        assertThrows(CommentException.class, () -> commentService.findById(postId, commentId));
    }


    @Test
    public void testDeleteComment_Success() {
        Long postId = 1L;
        Long commentId = 1L;
        Comment comment = Comment.builder()
                .id(commentId)
                .content("Fourth comment")
                .postId(postId)
                .userId(2L)
                .build();
        when(commentRepository.findByPostId(postId)).thenReturn(Arrays.asList(comment));
        doNothing().when(commentRepository).deleteById(commentId);

        assertDoesNotThrow(() -> commentService.deleteComment(postId, commentId));
    }

    @Test
    public void testDeleteComment_CommentNotFound() {
        Long postId = 1L;
        Long commentId = 1L;
        when(commentRepository.findByPostId(postId)).thenReturn(Arrays.asList());

        assertThrows(CommentException.class, () -> commentService.deleteComment(postId, commentId));
    }

    @Test
    public void testEditComment_Success() {
        Long postId = 1L;
        Long commentId = 1L;

        Comment originalComment = Comment.builder()
                .id(commentId)
                .content("Original content")
                .postId(postId)
                .userId(2L)
                .build();

        Comment updatedComment = Comment.builder()
                .id(commentId)
                .content("Updated content")
                .postId(postId)
                .userId(2L)
                .build();
        when(commentRepository.findByPostId(postId)).thenReturn(Arrays.asList(originalComment));
        when(commentRepository.save(any(Comment.class))).thenReturn(updatedComment);

        Comment editedComment = commentService.editComment(postId, commentId, updatedComment);

        assertNotNull(editedComment);
        assertEquals(updatedComment.getContent(), editedComment.getContent());
    }

    @Test
    public void testEditComment_CommentNotFound() {
        Long postId = 1L;
        Long commentId = 1L;
        Comment updatedComment = Comment.builder()
                .id(commentId)
                .content("Updated content")
                .postId(postId)
                .userId(2L)
                .build();
        when(commentRepository.findByPostId(postId)).thenReturn(Arrays.asList());

        assertThrows(CommentException.class, () -> commentService.editComment(postId, commentId, updatedComment));
    }
}