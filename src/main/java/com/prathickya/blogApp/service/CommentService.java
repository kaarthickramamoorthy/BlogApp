package com.prathickya.blogApp.service;

import com.prathickya.blogApp.dto.CommentDto;
import com.prathickya.blogApp.entity.Comment;

import java.util.Set;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    Set<CommentDto> getAllComments(long postId);
    CommentDto getCommentById(long postId, long commentId);
    CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto);
    void deleteComment(long postId, long commentId);
}
