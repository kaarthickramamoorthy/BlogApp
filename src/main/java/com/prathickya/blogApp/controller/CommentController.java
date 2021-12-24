package com.prathickya.blogApp.controller;

import com.prathickya.blogApp.dto.CommentDto;
import com.prathickya.blogApp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable(name = "postId") long postId) {
        final CommentDto commentResponseDto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<CommentDto>> getAllComments(@PathVariable(name = "postId") long postId) {
        return ResponseEntity.ok(commentService.getAllComments(postId));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "postId") long postId,
                                                     @PathVariable(name = "commentId") long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(
            @PathVariable(name = "postId") long postId,
            @PathVariable(name = "commentId") long commentId,
            @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.updateCommentById(postId, commentId, commentDto));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteCommentById(
            @PathVariable(name = "postId") long postId,
            @PathVariable(name = "commentId") long commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok(String.format("Comment with id : %s deleted successfully for PostId: %s",commentId, postId));
    }
}
