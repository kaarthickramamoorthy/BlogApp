package com.prathickya.blogApp.service.impl;

import com.prathickya.blogApp.dto.CommentDto;
import com.prathickya.blogApp.entity.Comment;
import com.prathickya.blogApp.entity.Post;
import com.prathickya.blogApp.exception.BlogAPIException;
import com.prathickya.blogApp.exception.ResourceNotFoundException;
import com.prathickya.blogApp.repository.CommentRepository;
import com.prathickya.blogApp.repository.PostRepository;
import com.prathickya.blogApp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        final Post post = getPost(postId);
        final Comment comment = new Comment(commentDto.getName(), commentDto.getEmail(), commentDto.getBody(), post);
        final Comment savedComment = commentRepository.save(comment);
        return new CommentDto(savedComment.getId(), savedComment.getName(), savedComment.getEmail(), savedComment.getBody());
    }

    @Override
    public Set<CommentDto> getAllComments(long postId) {
        final Post post = getPost(postId);
        final Set<Comment> comments = post.getComments();

        //Another way to find all Comments by Id
        //final List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream()
                .map(comment ->  new CommentDto(comment.getId(), comment.getName(), comment.getEmail(), comment.getBody()))
                .collect(Collectors.toSet());
    }

    private Post getPost(long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("POST", "id", postId + ""));
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        final Post post = getPost(postId);
        final Comment comment = getComment(commentId);

        if(comment.getPost().getId() != post.getId()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment Id: " + commentId + " does not belong to Post Id: " + postId);
        }

        return new CommentDto(comment.getId(), comment.getName(), comment.getEmail(), comment.getBody());
    }

    private Comment getComment(long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("COMMENT", "id", commentId + ""));
    }

    @Override
    public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto) {
        final Post post = getPost(postId);
        final Comment comment = getComment(commentId);

        if(comment.getPost().getId() != post.getId()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment Id: " + commentId + " does not belong to Post Id: " + postId);
        }

        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());

        final Comment updatedComment = commentRepository.save(comment);
        return new CommentDto(updatedComment.getId(), updatedComment.getName(), updatedComment.getEmail(), updatedComment.getBody());
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        final Post post = getPost(postId);
        final Comment comment = getComment(commentId);

        if(comment.getPost().getId() != post.getId()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment Id: " + commentId + " does not belong to Post Id: " + postId);
        }

        commentRepository.delete(comment);
    }
}
