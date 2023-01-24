package com.blog.controllers;

import com.blog.payloads.CommentDTO;
import com.blog.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private CommentService commentService;
    public CommentController (CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(name = "postId") long postId, @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(this.commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable(name = "postId") long postId) {
        return ResponseEntity.ok(this.commentService.getCommentsByPostId(postId));
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(name = "postId") long postId, @PathVariable(name = "commentId") long commentId) {
        return ResponseEntity.ok(this.commentService.getCommentById(postId, commentId));
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment (@PathVariable(name = "postId") long postId, @PathVariable(name = "commentId") long commentId, @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(this.commentService.updateComment(postId, commentId, commentDTO));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "postId") long postId, @PathVariable(name = "commentId") long commentId) {
        this.commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("Comment entity deleted successfully.");
    }
}
