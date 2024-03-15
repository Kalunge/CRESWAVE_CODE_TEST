package com.blog_api.blog_api.service.implementation;

import com.blog_api.blog_api.dto.CommentDto;
import com.blog_api.blog_api.entity.Comment;
import com.blog_api.blog_api.entity.Post;
import com.blog_api.blog_api.entity.User;
import com.blog_api.blog_api.exception.CommentException;
import com.blog_api.blog_api.exception.PostException;
import com.blog_api.blog_api.repository.CommentRepository;
import com.blog_api.blog_api.repository.PostRepository;
import com.blog_api.blog_api.repository.UserRepository;
import com.blog_api.blog_api.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImplementation implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentServiceImplementation(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment createComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostException("No post found with the given ID", "NOT_FOUND"));

        Comment commentToSave = Comment.builder().content(comment.getContent()).postId(post.getId()).userId(comment.getUserId()).build();

        return commentRepository.save(commentToSave);
    }

    @Override
    public List<Comment> getAllComments(Long postId) {
        return commentRepository.findByPostId(postId);
    }


    @Override
    public Optional<Comment> findById(Long postId, Long commentId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        Optional<Comment> optionalComment = Optional.of(comments.stream().filter(comm -> comm.getId().equals(commentId)).findFirst().get());

        return optionalComment;
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        Optional<Comment> optionalComment = Optional.of(comments.stream().filter(comm -> comm.getId().equals(commentId)).findFirst().get());
        commentRepository.deleteById(optionalComment.get().getId());
    }

    @Override
    public Comment editComment(Long postId, Long commentId, Comment comment) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        Optional<Comment> optionalComment = Optional.of(comments.stream().filter(comm -> comm.getId().equals(commentId)).findFirst().get());
        Comment editedComment = optionalComment.get();
        editedComment.setContent(comment.getContent());
        return commentRepository.save(editedComment);


    }
}
